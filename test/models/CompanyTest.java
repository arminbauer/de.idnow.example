package models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CompanyTest {

    private static final double EQUALS_DELTA = 0.00001;

    @Test
    public void testgetMissingSlaPercentage1() {
        Company c = new Company();
        assertEquals(0, c.getSlaPercentage(), EQUALS_DELTA);
        assertEquals(0, c.getCurrentSlaPercentage(), EQUALS_DELTA);
        assertEquals(0, c.getMissingSlaPercentage(), EQUALS_DELTA);
    }

    @Test
    public void testgetMissingSlaPercentage2() {
        Company c = new Company();
        c.setSlaPercentage(1);
        c.setCurrentSlaPercentage(1);
        assertEquals(0, c.getMissingSlaPercentage(), EQUALS_DELTA);
    }

    @Test
    public void testgetMissingSlaPercentage3() {
        Company c = new Company();
        c.setSlaPercentage(0.9);
        c.setCurrentSlaPercentage(0.8);
        assertEquals(0.1, c.getMissingSlaPercentage(), EQUALS_DELTA);
    }

    @Test
    public void testCompare1() {
        Company c1 = new Company();
        Company c2 = new Company();
        assertEquals(0, c1.compareTo(c2));
    }

    @Test
    public void testCompare2() {
        Company c1 = new Company();
        c1.setSlaTime(20);

        Company c2 = new Company();
        c2.setSlaTime(10);
        assertTrue("Smaller SlaTime must be first", c1.compareTo(c2) > 0);
    }

    @Test
    public void testCompare3() {
        Company c1 = new Company();
        c1.setSlaTime(10);
        c1.setCurrentSlaPercentage(0.5);
        c1.setSlaPercentage(0.9);

        Company c2 = new Company();
        c2.setSlaTime(10);
        c2.setCurrentSlaPercentage(0.1);
        c2.setSlaPercentage(0.9);

        assertTrue("Bigger missing Sla % must be first", c1.compareTo(c2) > 0);
    }

    @Test
    public void testCompare4() {
        Company c1 = new Company();
        c1.setSlaTime(10);
        c1.setCurrentSlaPercentage(0.5);
        c1.setSlaPercentage(0.9);

        Company c2 = new Company();
        c2.setSlaTime(20);
        c2.setCurrentSlaPercentage(0.1);
        c2.setSlaPercentage(0.9);

        assertTrue("Smaller SlaTime must be first", c1.compareTo(c2) < 0);
    }

}
