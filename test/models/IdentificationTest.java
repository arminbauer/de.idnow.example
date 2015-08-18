package models;

import org.junit.Assert;
import org.junit.Test;

public class IdentificationTest {

    @Test
    public void testCompare1() {
        Company c = new Company();
        c.setSlaPercentage(0.9);
        c.setCurrentSlaPercentage(0.95);

        Identification i1 = new Identification();
        i1.setWaitingTime(30);
        i1.setCompany(c);

        Identification i2 = new Identification();
        i2.setWaitingTime(45);
        i2.setCompany(c);

        Assert.assertTrue(i1.compareTo(i2) > 0);
    }

    @Test
    public void testCompare2() {
        Company c1 = new Company();
        c1.setSlaTime(20);

        Identification i1 = new Identification();
        i1.setWaitingTime(30);
        i1.setCompany(c1);

        Company c2 = new Company();
        c2.setSlaTime(10);

        Identification i2 = new Identification();
        i2.setWaitingTime(30);
        i2.setCompany(c2);

        Assert.assertTrue(i1.compareTo(i2) > 0);
    }

}
