package dao;

import com.google.common.collect.Lists;
import models.Company;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by nick on 14.05.16.
 */
public class InMemoryCompanyStoreIntegrationTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private CompanyStore store;

    @Before
    public void setUp() throws Exception {
        store = new InMemoryCompanyStore();
    }

    @Test
    public void should_add_new_company() throws Exception {
        Company company = randomCompany();

        store.upsert(company);
        Long companyId = company.getId();
        Assert.assertThat("Should create company", companyId, Matchers.greaterThan(0L));

        String randomCompanyName = randomAlphanumeric(128);
        company.setName(randomCompanyName);
        store.upsert(company);

        Assert.assertThat("Company should be updated", company.getId(), is(companyId));
        Company fetched = store.findByIdOrThrow(company.getId());
        Assert.assertThat(fetched, is(company));
        Assert.assertThat(fetched.getName(), Matchers.is(randomCompanyName));
    }

    @Test
    public void should_throw_exception_when_no_entity() throws Exception {
        exception.expect(EntityNotFountException.class);
        exception.expectMessage(containsString("Company doesn't exist"));
        store.findByIdOrThrow(RandomUtils.nextLong(0, Long.MAX_VALUE));
        Assert.fail("should never happen");
    }

    @Test
    public void should_return_absent_when_no_entity() throws Exception {
        Company company = randomCompany();
        store.upsert(company);
        Assert.assertThat(company.getId(), Matchers.greaterThan(0L));
        Optional<Company> companyById = store.findById(company.getId());
        Assert.assertThat(companyById.isPresent(), Matchers.is(true));
        Assert.assertThat(companyById.get(), Matchers.is(company));
        Assert.assertThat(store.findById(RandomUtils.nextLong(0, Long.MAX_VALUE)).isPresent(), Matchers.is(false));
    }


    @Test
    public void should_return_all_companies() throws Exception {
        Company company1 = randomCompany();
        Company company2 = randomCompany();
        Company company3 = randomCompany();
        //
        store.upsert(company3);
        store.upsert(company1);
        store.upsert(company2);
        //
        List<Company> companies = Lists.newArrayList(store.findAll());
        Assert.assertThat(companies, Matchers.hasSize(3));
        Assert.assertThat(companies, Matchers.hasItem(company1));
        Assert.assertThat(companies, Matchers.hasItem(company2));
        Assert.assertThat(companies, Matchers.hasItem(company3));
    }

    private Company randomCompany() {
        Company company = new Company();
        company.setName(RandomStringUtils.randomAlphabetic(12));
        company.setSlaTimeSeconds(RandomUtils.nextInt(50, 200));
        company.setSlaPercentage(RandomUtils.nextFloat(0.80F, 0.95F));
        company.setCurrentSlaPercentage(RandomUtils.nextFloat(0.80F, 0.95F));
        return company;
    }


}