package services;

import models.Company;
import models.Identification;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class IdentificationServiceTest {

    private IdentificationService is;

    @Before
    public void setUp() throws Exception {
        is = new IdentificationService();
    }

    @Test
    public void prioritizeWithinCompany() {

        UUID companyId = UUID.randomUUID();

        ArrayList<Identification> list = new ArrayList<>();
        Identification ident1 = new Identification(UUID.randomUUID(), "Name Unknown 1", 123L, 30L, companyId);
        Identification ident2 = new Identification(UUID.randomUUID(), "Name Unknown 2", 123L, 40L, companyId);
        list.add(ident1);
        list.add(ident2);

        List<Identification> identificationsPrioritized = is.prioritizeWithinCompany(list);

        assertEquals(40L, identificationsPrioritized.get(0).getWaitingTime().longValue());
        assertEquals(30L, identificationsPrioritized.get(1).getWaitingTime().longValue());
    }

    @Test
    public void prioritizeCompanies_SLA_Time() {
        UUID companyId1 = UUID.randomUUID();
        UUID companyId2 = UUID.randomUUID();

        Company company1 = new Company(companyId1, "Company 1", 61L, 0.9f, 0.9f);
        Company company2 = new Company(companyId2, "Company 2", 60L, 0.9f, 0.9f);

        ArrayList<Company> list = new ArrayList<>();
        list.add(company1);
        list.add(company2);

        List<Company> prioritizedCompanies = is.prioritizeCompanies(list);

        assertEquals(60L, prioritizedCompanies.get(0).getSlaTime().longValue());
        assertEquals(61L, prioritizedCompanies.get(1).getSlaTime().longValue());
    }

    @Test
    public void prioritizeCompanies_SLA_Percentage() {
        UUID companyId1 = UUID.randomUUID();
        UUID companyId2 = UUID.randomUUID();

        Company company1 = new Company(companyId1, "Company 1", 60L, 0.9f, 0.9f);
        Company company2 = new Company(companyId2, "Company 2", 60L, 0.8f, 0.9f);

        ArrayList<Company> list = new ArrayList<>();
        list.add(company1);
        list.add(company2);

        List<Company> prioritizedCompanies = is.prioritizeCompanies(list);

        assertEquals(0.9f, prioritizedCompanies.get(0).getSlaPercentage(), 0.0f);
        assertEquals(0.8f, prioritizedCompanies.get(1).getSlaPercentage(), 0.0f);
    }

    @Test
    public void prioritizeCompanies_Current_SLA_Percentage() {
        UUID companyId1 = UUID.randomUUID();
        UUID companyId2 = UUID.randomUUID();

        Company company1 = new Company(companyId1, "Company 1", 60L, 0.8f, 0.9f);
        Company company2 = new Company(companyId2, "Company 2", 60L, 0.8f, 0.85f);

        ArrayList<Company> list = new ArrayList<>();
        list.add(company1);
        list.add(company2);

        List<Company> prioritizedCompanies = is.prioritizeCompanies(list);

        assertEquals(0.85f, prioritizedCompanies.get(0).getCurrentSLAPercentage(), 0.0f);
        assertEquals(0.9f, prioritizedCompanies.get(1).getCurrentSLAPercentage(), 0.0f);
    }
}