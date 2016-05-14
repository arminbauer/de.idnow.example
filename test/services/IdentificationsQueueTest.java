package services;


import dao.EntityNotFountException;
import dao.InMemoryCompanyStore;
import models.Company;
import models.Identification;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static services.IdentificationsQueue.CompanyComparator;
import static services.IdentificationsQueue.IdentificationComparator;

/**
 * Created by nick on 13.05.16.
 */
public class IdentificationsQueueTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private IdentificationsQueue queue;
    private InMemoryCompanyStore companyRepository;

    @Before
    public void setUp() throws Exception {
        companyRepository = Mockito.spy(new InMemoryCompanyStore());
        queue = new IdentificationsQueue(companyRepository);
    }

    @Test
    public void should_accept_idents_when_company_id_not_null() throws Exception {
        exception.expect(EntityNotFountException.class);
        exception.expectMessage("Company doesn't exist! CompanyId='");
        Identification ident = new Identification();
        ident.setId(RandomUtils.nextLong(0, Long.MAX_VALUE));
        queue.add(ident);
        Assert.fail("should never happen");
    }

    @Test
    public void should_accept_only_saved_idents() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Identification must be saved before!");
        queue.add(new Identification());
        Assert.fail("should never happen");
    }

    @Test
    public void should_throw_exception_when_add_duplicate() throws Exception {
        Company company = randomCompany(10, 0.9F, 0.9F);
        companyRepository.upsert(company);
        Identification ident = randomIdentification(100, company.getId());
        queue.add(ident);
        exception.expect(IdentificationsQueueDuplicateException.class);
        queue.add(ident);
        Assert.fail("should never happen");
    }

    @Test
    public void should_be_possible_to_add_idents_to_queue() throws Exception {
        Company company = randomCompany(80, 0.99F, 0.99F);
        companyRepository.upsert(company);

        assertThat(queue.isEmpty(), is(true));
        assertThat(queue.size(), is(0));

        queue.add(randomIdentification(120, company.getId()));
        queue.add(randomIdentification(80, company.getId()));

        assertThat(queue.size(), is(2));
    }

    @Test
    public void idents_should_be_compared_by_wait_time_when_companies_equal() throws Exception {
        Identification prototype = randomIdentification();
        //
        Company company = Mockito.mock(Company.class);
        Mockito.doReturn(company).when(companyRepository).findByIdOrThrow(Mockito.eq(prototype.getCompanyId()));

        CompanyComparator companyComparator = Mockito.mock(CompanyComparator.class);
        Mockito.when(companyComparator.compare(Mockito.eq(company), Mockito.eq(company))).thenReturn(0);
        //
        Identification fastIdent = SerializationUtils.clone(prototype);
        fastIdent.setWaitTimeSeconds(RandomUtils.nextInt(25, 35));

        //      
        Identification slowIdent = SerializationUtils.clone(prototype);
        slowIdent.setWaitTimeSeconds(RandomUtils.nextInt(40, 50));

        //
        assertThat(new IdentificationComparator(companyRepository, companyComparator).compare(fastIdent, slowIdent), is(1));
        Mockito.verify(companyRepository, Mockito.times(2)).findByIdOrThrow(prototype.getCompanyId());
        Mockito.verify(companyComparator).compare(Mockito.eq(company), Mockito.eq(company));
    }

    /**
     * Example 1:
     * <p>
     * One company with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
     * Identification 1: Waiting_time=30
     * Identification 2: Waiting_time=45 Expected order: Identification 2, Identification 1 (since Ident 2 has waited longer)
     */
    @Test
    public void idents_should_be_compared_by_wait_time_within_same_company() throws Exception {
        Company company = randomCompany(50, 0.90F, 0.95F);
        companyRepository.upsert(company);
        Identification lessWait = randomIdentification(30, company.getId());
        Identification moreWait = randomIdentification(45, company.getId());
        queue.add(lessWait);
        queue.add(moreWait);

        assertThat(queue.size(), is(2));

        assertThat(queue.poll(), is(moreWait));
        assertThat(queue.poll(), is(lessWait));
        assertThat(queue.isEmpty(), is(true));
    }

    /**
     * Example 2:
     * <p>
     * Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
     * Company 2 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.90
     * Identification 1 belonging to Company1: Waiting_time=30
     * Identification 2 belonging to Company2: Waiting_time=30 Expected order: Identification 2, Identification 1
     * (since Company 2 already has a lower current SLA percentage in this month, so its getIdentifications have higher prio)
     */
    @Test
    public void then_lower_company_sla_percentage_then_higher_priority() throws Exception {
        Company companyWithHighSlaPercentage = randomCompany(60, 0.9F, 0.95F);
        Identification identHigh = randomIdentification(30, companyWithHighSlaPercentage.getId());

        companyRepository.upsert(companyWithHighSlaPercentage);
        //
        Company companyWithLowSlaPercentage = randomCompany(60, 0.9F, 0.90F);
        Identification identLow = randomIdentification(30, companyWithLowSlaPercentage.getId());

        companyRepository.upsert(companyWithLowSlaPercentage);
        //
        Company companyWithMiddleSlaPercentage = randomCompany(60, 0.9F, 0.92F);
        Identification identMiddle = randomIdentification(30, companyWithMiddleSlaPercentage.getId());

        companyRepository.upsert(companyWithMiddleSlaPercentage);
        //
        queue.add(identMiddle);
        queue.add(identLow);
        queue.add(identHigh);


        assertThat(queue.size(), is(3));
        assertThat(queue.poll(), is(identLow));
        assertThat(queue.poll(), is(identMiddle));
        assertThat(queue.poll(), is(identHigh));
        assertThat(queue.isEmpty(), is(true));

    }

    /**
     * Example 3:
     * <p>
     * Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
     * Company 2 with SLA_time=120, SLA_percentage=0.8, Current_SLA_percentage=0.95
     * Identification 1 belonging to Company1: Waiting_time=30
     * Identification 2 belonging to Company2: Waiting_time=30 Expected order: Identification 1, Identification 2 (since company 1 has a lower, more urgent SLA)
     */
    @Test
    public void use_lowest_sla_time_when_current_sla_percentage_equal() throws Exception {
        Company companyFastRespond = randomCompany(60, 0.9F, 0.95F);
        Identification ident1 = randomIdentification(30, companyFastRespond.getId());
        companyRepository.upsert(companyFastRespond);
        //
        Company companyMiddleRespond = randomCompany(80, 0.8F, 0.95F);
        Identification ident2 = randomIdentification(30, companyMiddleRespond.getId());
        companyRepository.upsert(companyMiddleRespond);
        //
        Company companySlowRespond = randomCompany(120, 0.8F, 0.95F);
        Identification ident3 = randomIdentification(30, companySlowRespond.getId());
        companyRepository.upsert(companySlowRespond);

        //
        queue.add(ident2);
        queue.add(ident3);
        queue.add(ident1);

        assertThat(queue.size(), is(3));
        assertThat(queue.poll(), is(ident1));
        assertThat(queue.poll(), is(ident2));
        assertThat(queue.poll(), is(ident3));
        assertThat(queue.isEmpty(), is(true));

    }

    /**
     * Example 4:
     * <p>
     * Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
     * Company 2 with SLA_time=120, SLA_percentage=0.8, Current_SLA_percentage=0.80
     * Identification 1 belonging to Company1: Waiting_time=45
     * Identification 2 belonging to Company2: Waiting_time=30
     * Expected order: Identification 2 (since Current_SLA_percentage is lower), Identification 1
     * BUT: There might more complex score-base algorithm.
     */
    @Test
    public void then_lower_company_sla_percentage_then_higher_priority_duplicate() throws Exception {
        Company companyWithHighSlaPercentage = randomCompany(60, 0.9F, 0.95F);
        Identification identLow = randomIdentification(45, companyWithHighSlaPercentage.getId());
//
        Company companyWithLowSlaPercentage = randomCompany(120, 0.8F, 0.8F);
        Identification identHigh = randomIdentification(45, companyWithLowSlaPercentage.getId());
        //
        companyRepository.upsert(companyWithHighSlaPercentage);
        companyRepository.upsert(companyWithLowSlaPercentage);
        //
        queue.add(identHigh);
        queue.add(identLow);

        assertThat(queue.size(), is(2));
        assertThat(queue.poll(), is(identHigh));
        assertThat(queue.poll(), is(identLow));
        assertThat(queue.isEmpty(), is(true));
    }

    private Identification randomIdentification() {
        return randomIdentification(RandomUtils.nextInt(20, 60), RandomUtils.nextLong(1, Long.MAX_VALUE));
    }

    private Identification randomIdentification(int waitTime, Long companyId) {
        Identification identification = new Identification();
        identification.setCompanyId(companyId);
        identification.setWaitTimeSeconds(waitTime);
        identification.setId(RandomUtils.nextLong(100, Long.MAX_VALUE));
        return identification;
    }

    private Company randomCompany(int slaTime, float slaPercentage, float currentSlaPercentage) {
        Company company = new Company();
        company.setId(RandomUtils.nextLong(100, Long.MAX_VALUE));
        company.setSlaTimeSeconds(slaTime);
        company.setSlaPercentage(slaPercentage);
        company.setCurrentSlaPercentage(currentSlaPercentage);
        return company;
    }
}