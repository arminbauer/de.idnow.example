import engine.IdentificationSystem;
import entities.Company;
import entities.Identification;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by vld on 11/08/16.
 */
public class IdentificationSystemTest {

    IdentificationSystem sut;

    @Before
    public void setUp() {
        sut = new IdentificationSystem();
    }

    // TODO: More test required for combinations and priority of different sort criterias

    @Test
    public void queueIdentificaton_provided_queued() {
        sut.queueIdentificaton(new Identification("1", "A", 1000, 10,
                new Company("1", "AC", 10, 90, 80)));
        List<Identification> identifications = sut.getPendingIdentifications();
        Assert.assertEquals(1, identifications.size());
        Assert.assertEquals("1", identifications.get(0).getId());
    }

    @Test
    public void getPendingIdentifications_waitingTime_biggerFirst() {
        Company company = new Company("1", "AC", 60, 90, 95);
        sut.queueIdentificaton(new Identification("3", "C", 1000, 25, company));
        sut.queueIdentificaton(new Identification("1", "A", 1000, 30, company));
        sut.queueIdentificaton(new Identification("2", "B", 1000, 45, company));

        List<Identification> identifications = sut.getPendingIdentifications();
       // TODO: better to be done with the new Harmcrest contains
        Assert.assertEquals("B", identifications.get(0).getName());
        Assert.assertEquals("A", identifications.get(1).getName());
        Assert.assertEquals("C", identifications.get(2).getName());
    }

    @Test
    public void getPendingIdentifications_currentSla_smallerFirst() {
        sut.queueIdentificaton(new Identification("1", "A", 1000, 60, new Company("1", "AC", 60, 90, 95)));
        sut.queueIdentificaton(new Identification("2", "B", 1000, 60, new Company("2", "BC", 60, 90, 90)));
        sut.queueIdentificaton(new Identification("3", "C", 1000, 60, new Company("3", "CC", 60, 90, 85)));

        List<Identification> identifications = sut.getPendingIdentifications();
        // TODO: better to be done with the new Harmcrest contains
        Assert.assertEquals("C", identifications.get(0).getName());
        Assert.assertEquals("B", identifications.get(1).getName());
        Assert.assertEquals("A", identifications.get(2).getName());
    }

    @Test
    public void getPendingIdentifications_slaTime_smallerFirst() {

        sut.queueIdentificaton(new Identification("1", "A", 1000, 30, new Company("1", "AC", 60, 90, 95)));
        sut.queueIdentificaton(new Identification("2", "B", 1000, 30, new Company("2", "BC", 120, 90, 95)));
        sut.queueIdentificaton(new Identification("3", "C", 1000, 30, new Company("3", "CC", 30, 90, 95)));

        List<Identification> identifications = sut.getPendingIdentifications();
        // TODO: better to be done with the new Harmcrest contains
        Assert.assertEquals("C", identifications.get(0).getName());
        Assert.assertEquals("A", identifications.get(1).getName());
        Assert.assertEquals("B", identifications.get(2).getName());
    }
}
