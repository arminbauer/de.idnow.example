package services;

import models.Company;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

/**
 * Created by nick on 13.05.16.
 */
public class CompanyComparatorTest {

    private IdentificationsQueue.CompanyComparator comparator;

    @Before
    public void setUp() throws Exception {
        comparator = new IdentificationsQueue.CompanyComparator();
    }

    @Test
    public void companies_should_be_compared_by_multiple_field() throws Exception {
        Company company1 = randomCompany(30, 0.9F, 0.95F);
        Company company2 = randomCompany(30, 0.9F, 0.95F);
        Assert.assertThat(comparator.compare(company1, company2), is(0));
    }

    @Test
    public void then_lower_current_sla_then_higher_priority() throws Exception {
        Company company1 = randomCompany(30, 0.9F, 0.95F);
        Company company2 = randomCompany(30, 0.9F, 0.90F);
        Assert.assertThat(comparator.compare(company1, company2), is(1));
    }

    @Test
    public void then_lower_sla_time_then_higher_priority_when_current_sla_equal() throws Exception {
        Company company1 = randomCompany(60, 0.9F, 0.95F);
        Company company2 = randomCompany(120, 0.8F, 0.95F);
        Assert.assertThat(comparator.compare(company1, company2), is(-1));
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