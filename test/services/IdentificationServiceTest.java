package services;

import models.Company;
import models.Identification;
import org.junit.Test;

import java.util.SortedSet;

import static org.junit.Assert.assertEquals;

/**
 * Created by Talal Ahmed on 19/08/2018
 */
public class IdentificationServiceTest {

    @Test
    public void testAddCompany() {
        IdentificationService identificationService = new IdentificationService();

        identificationService.addCompany(new Company.Builder().id(1).name("C1").get());
        identificationService.addCompany(new Company.Builder().id(2).name("C2").get());

        assertEquals(identificationService.getCompanies().size(), 2);
    }

    @Test
    public void testAddIdentification() {
        IdentificationService identificationService = new IdentificationService();

        identificationService.addIdentification(new Identification.Builder().id(1).name("ID1").get());
        identificationService.addIdentification(new Identification.Builder().id(2).name("ID2").get());

        assertEquals(identificationService.getIdentifications().size(), 2);
    }

    @Test
    public void testPriorityList() {
        IdentificationService identificationService = new IdentificationService();

        Company c1 = new Company.Builder().id(1).slaTime(60).slaPercentage(0.9).currentSLAPercentage(0.95).get();
        Company c2 = new Company.Builder().id(2).slaTime(60).slaPercentage(0.9).currentSLAPercentage(0.90).get();

        identificationService.addCompany(c1);
        identificationService.addCompany(c2);

        Identification ident1 = new Identification.Builder()
                .id(1)
                .time(System.currentTimeMillis())
                .waitingTime(30)
                .companyId(c1.getId())
                .get();


        Identification ident2 = new Identification.Builder()
                .id(2)
                .name("B")
                .time(System.currentTimeMillis())
                .waitingTime(30)
                .companyId(c2.getId())
                .get();

        identificationService.addIdentification(ident1);
        identificationService.addIdentification(ident2);

        SortedSet<Identification> identifications = identificationService.priorityList();

        assertEquals(identifications.first().getId(), ident2.getId());
        assertEquals(identifications.last().getId(), ident1.getId());
    }
}
