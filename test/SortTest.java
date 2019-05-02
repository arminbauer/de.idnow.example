import model.Company;
import model.Identification;
import model.IdentificationSorter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SortTest {
    @Test
    public void testScorableClass() {
        Company company1 = new Company();
        company1.setId(1);
        company1.setName("Test Bank 1");
        company1.setSla_time(60);
        company1.setSla_percentage(0.9);
        company1.setCurrent_sla_percentage(0.95);

        Identification id1 = new Identification();
        id1.setCompany(company1);
        id1.setId(1);
        id1.setName("Sam");
        id1.setWaiting_time(120);

        assertEquals(id1.score(), 1.55, 0.1);
    }

    @Test
    public void testScorableClass_CompanyIsNull() {
        Identification id1 = new Identification();
        id1.setId(1);
        id1.setName("Frodo");
        id1.setWaiting_time(120);

        assertEquals(id1.score(), 0.0, 0.0);
    }

    @Test
    public void testScorableClass_DividedByZero() {
        Company company1 = new Company();
        company1.setId(1);
        company1.setName("Test Bank 1");
        company1.setSla_time(60);
        company1.setSla_percentage(0.9);
        company1.setCurrent_sla_percentage(0.9);

        Identification id1 = new Identification();
        id1.setId(1);
        id1.setCompany(company1);
        id1.setName("Frodo");
        id1.setWaiting_time(0);

        assertEquals(id1.score(), 0.0, 0.0);

        Company company2 = new Company();
        company2.setId(2);
        company2.setName("Test Bank 2");
        company2.setSla_time(60);
        company2.setSla_percentage(0.0);
        company2.setCurrent_sla_percentage(4.0);

        Identification id2 = new Identification();
        id2.setId(2);
        id2.setCompany(company2);
        id2.setName("Baggins");
        id2.setWaiting_time(120);

        assertEquals(id2.score(), 0.0, 0.0);
    }

    @Test
    public void testScorableClass_Comparison() {
        /* Company 1 */
        Company company1 = new Company();
        company1.setId(1);
        company1.setName("Test Bank 1");
        company1.setSla_time(60);
        company1.setSla_percentage(0.9);
        company1.setCurrent_sla_percentage(0.95);

        Identification id1 = new Identification();
        id1.setCompany(company1);
        id1.setCompanyid(company1.getId());
        id1.setId(1);
        id1.setName("Frodo");
        id1.setWaiting_time(120);

        /* Company 2 */
        Company company2 = new Company();
        company2.setId(2);
        company2.setName("Test Bank 2");
        company2.setSla_time(60);
        company2.setSla_percentage(0.8);
        company2.setCurrent_sla_percentage(0.78);

        Identification id2 = new Identification();
        id2.setCompany(company2);
        id2.setCompanyid(company2.getId());
        id2.setId(2);
        id2.setName("Sam");
        id2.setWaiting_time(120);

        /* Company 3 */
        Company company3 = new Company();
        company3.setId(3);
        company3.setName("Test Bank 3");
        company3.setSla_time(120);
        company3.setSla_percentage(0.9);
        company3.setCurrent_sla_percentage(0.9);

        Identification id3 = new Identification();
        id3.setCompany(company3);
        id3.setCompanyid(company3.getId());
        id3.setId(3);
        id3.setName("Ken");
        id3.setWaiting_time(120);

        List<Identification> listIdent = new ArrayList<>();
        listIdent.add(id1);
        listIdent.add(id2);
        listIdent.add(id3);

        // Assert the correct order of sorted list
        listIdent.sort(new IdentificationSorter());
        assertEquals(listIdent.get(0), id2);
        assertEquals(listIdent.get(1), id1);
        assertEquals(listIdent.get(2), id3);
    }

}
