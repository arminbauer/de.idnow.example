import models.Company;
import models.Identification;
import org.junit.Test;
import utils.sorter.IdentificationSorter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class IdentificationsSorterTest {

    @Test
    public void sortIdentificationsExample1() throws Exception {

        List<Identification> identificationList = new ArrayList<>();

        Company company = new Company();
        company.setId(1);
        company.setName("Test Company");
        company.setSlaTime(60);
        company.setSlaPercentage(0.9f);
        company.setCurrentSlaPercentage(0.95f);

        Identification identification1 = new Identification();
        identification1.setId(1);
        identification1.setName("Peter Huber");
        identification1.setTime(1435667215);
        identification1.setWaitingTime(30);
        identification1.setCompany(company);
        identificationList.add(identification1);

        Identification identification2 = new Identification();
        identification2.setId(2);
        identification2.setName("Peter Maier");
        identification2.setTime(1435667215);
        identification2.setWaitingTime(45);
        identification2.setCompany(company);
        identificationList.add(identification2);

        Stream<Identification> identificationStream = IdentificationSorter.sortIdentifications(identificationList);

        assertTrue(identificationStream.findFirst().get().equals(identification2));

    }

    @Test
    public void sortIdentificationsExample2() throws Exception {

        List<Identification> identificationList = new ArrayList<>();

        Company company1 = new Company();
        company1.setId(1);
        company1.setName("Test Company1");
        company1.setSlaTime(60);
        company1.setSlaPercentage(0.9f);
        company1.setCurrentSlaPercentage(0.95f);

        Company company2 = new Company();
        company2.setId(1);
        company2.setName("Test Company2");
        company2.setSlaTime(60);
        company2.setSlaPercentage(0.9f);
        company2.setCurrentSlaPercentage(0.9f);


        Identification identification1 = new Identification();
        identification1.setId(1);
        identification1.setName("Peter Huber");
        identification1.setTime(1435667215);
        identification1.setWaitingTime(30);
        identification1.setCompany(company1);
        identificationList.add(identification1);

        Identification identification2 = new Identification();
        identification2.setId(2);
        identification2.setName("Peter Maier");
        identification2.setTime(1435667215);
        identification2.setWaitingTime(30);
        identification2.setCompany(company2);
        identificationList.add(identification2);

        Stream<Identification> identificationStream = IdentificationSorter.sortIdentifications(identificationList);

        assertTrue(identificationStream.findFirst().get().equals(identification2));

    }

    @Test
    public void sortIdentificationsExample3() throws Exception {

        List<Identification> identificationList = new ArrayList<>();

        Company company1 = new Company();
        company1.setId(1);
        company1.setName("Test Company1");
        company1.setSlaTime(60);
        company1.setSlaPercentage(0.9f);
        company1.setCurrentSlaPercentage(0.95f);

        Company company2 = new Company();
        company2.setId(1);
        company2.setName("Test Company2");
        company2.setSlaTime(120);
        company2.setSlaPercentage(0.8f);
        company2.setCurrentSlaPercentage(0.95f);


        Identification identification1 = new Identification();
        identification1.setId(1);
        identification1.setName("Peter Huber");
        identification1.setTime(1435667215);
        identification1.setWaitingTime(30);
        identification1.setCompany(company1);
        identificationList.add(identification1);

        Identification identification2 = new Identification();
        identification2.setId(2);
        identification2.setName("Peter Maier");
        identification2.setTime(1435667215);
        identification2.setWaitingTime(30);
        identification2.setCompany(company2);
        identificationList.add(identification2);

        Stream<Identification> identificationStream = IdentificationSorter.sortIdentifications(identificationList);

        assertTrue(identificationStream.findFirst().get().equals(identification1));

    }

    @Test
    public void sortIdentificationsExample4() throws Exception {

        List<Identification> identificationList = new ArrayList<>();

        Company company1 = new Company();
        company1.setId(1);
        company1.setName("Test Company1");
        company1.setSlaTime(60);
        company1.setSlaPercentage(0.9f);
        company1.setCurrentSlaPercentage(0.95f);

        Company company2 = new Company();
        company2.setId(1);
        company2.setName("Test Company2");
        company2.setSlaTime(120);
        company2.setSlaPercentage(0.8f);
        company2.setCurrentSlaPercentage(0.8f);


        Identification identification1 = new Identification();
        identification1.setId(1);
        identification1.setName("Peter Huber");
        identification1.setTime(1435667215);
        identification1.setWaitingTime(45);
        identification1.setCompany(company1);
        identificationList.add(identification1);

        Identification identification2 = new Identification();
        identification2.setId(2);
        identification2.setName("Peter Maier");
        identification2.setTime(1435667215);
        identification2.setWaitingTime(30);
        identification2.setCompany(company2);
        identificationList.add(identification2);

        Stream<Identification> identificationStream = IdentificationSorter.sortIdentifications(identificationList);

        assertTrue(identificationStream.findFirst().get().equals(identification1));

    }
}
