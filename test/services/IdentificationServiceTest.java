package services;

import com.google.common.collect.Sets;
import models.Company;
import models.Identification;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by bduisenov on 18/02/16.
 */
public class IdentificationServiceTest {

    private IdentificationService service = new IdentificationService();

    /**
     * Example 1:
     * One company with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
     * Identification 1: Waiting_time=30
     * Identification 2: Waiting_time=45
     *
     * Expected order: Identification 2, Identification 1 (since Ident 2 has waited longer)
     * @throws Exception
     */
    @Test
    public void testSortExample1() throws Exception {
        String companyName1 = "Company1";

        final Identification id1 = new Identification(companyName1, "example1 Id1", "Person1", 1000L, 30);
        final Identification id2 = new Identification(companyName1, "example1 Id2", "Person2", 1000L, 45);
        final List<Identification> sorted = service.sort(
                Sets.newHashSet(
                        new Company(companyName1, companyName1, 60, .9f, .95f)
                ),
                Arrays.asList(id1, id2)
        );

        assertEquals(Arrays.asList(id2, id1), sorted);
    }

    /**
     * Example 2:
     * Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
     * Company 2 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.90
     * Identification 1 belonging to Company1: Waiting_time=30
     * Identification 2 belonging to Company2: Waiting_time=30
     *
     * Expected order: Identification 2, Identification 1
     * (since Company 2 already has a lower current SLA percentage in this month, so its identifications have higher prio)
     * @throws Exception
     */
    @Test
    public void testSortExample2() throws Exception {
        String companyName2 = "Company2";
        String companyName3 = "Company3";

        final Identification id1 = new Identification(companyName2, "example2 Id1", "Person3", 1000L, 30);
        final Identification id2 = new Identification(companyName3, "example2 Id2", "Person4", 1000L, 30);
        final List<Identification> sorted = service.sort(
                Sets.newHashSet(
                        new Company(companyName2, companyName2, 60, .9f, .95f),
                        new Company(companyName3, companyName3, 60, .9f, .90f)
                ),
                Arrays.asList(id1, id2)
        );

        assertEquals(Arrays.asList(id2, id1), sorted);
    }

    /**
     * Example 3:
     * Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
     * Company 2 with SLA_time=120, SLA_percentage=0.8, Current_SLA_percentage=0.95
     * Identification 1 belonging to Company1: Waiting_time=30
     * Identification 2 belonging to Company2: Waiting_time=30
     *
     * Expected order: Identification 1, Identification 2 (since company 1 has a lower, more urgent SLA)
     * @throws Exception
     */
    @Test
    public void testSortExample3() throws Exception {
        String companyName4 = "Company4";
        String companyName5 = "Company5";

        final Identification id1 = new Identification(companyName4, "example3 Id1", "Person5", 1000L, 30);
        final Identification id2 = new Identification(companyName5, "example3 Id2", "Person6", 1000L, 30);
        final List<Identification> sorted = service.sort(
                Sets.newHashSet(
                        new Company(companyName4, companyName4, 60, .9f, .95f),
                        new Company(companyName5, companyName5, 120, .8f, .95f)
                ),
                Arrays.asList(id1, id2)
        );

        assertEquals(Arrays.asList(id1, id2), sorted);
    }

    /**
     * Example 4:
     * Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
     * Company 2 with SLA_time=120, SLA_percentage=0.8, Current_SLA_percentage=0.80
     * Identification 1 belonging to Company1: Waiting_time=45
     * Identification 2 belonging to Company2: Waiting_time=30
     *
     * What is the expected order here?
     * @throws Exception
     */
    @Test
    public void testSortExample4() throws Exception {
        String companyName6 = "Company6";
        String companyName7 = "Company7";

        final Identification id1 = new Identification(companyName6, "example4 Id1", "Person7", 1000L, 45);
        final Identification id2 = new Identification(companyName7, "example4 Id2", "Person8", 1000L, 30);
        final List<Identification> sorted = service.sort(
                Sets.newHashSet(
                        new Company(companyName6, companyName6, 60, .9f, .95f),
                        new Company(companyName7, companyName7, 120, .8f, .80f)
                ),
                Arrays.asList(id1, id2));

        assertEquals(Arrays.asList(id2, id1), sorted);
    }

}
