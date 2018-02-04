package persistence;

import org.junit.Test;
import play.libs.Json;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author prasa on 04-02-2018
 * @project de.idnow.example
 */
public class IdentificationJpaTest extends AbstractJpaTest {

    @Test
    public void test() {
        em.getTransaction().begin();
        Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
        CompanyEntity company = new CompanyEntity(1L, "Test Bank", 60, 0.9, 0.95);
        em.persist(company);
        IdentificationEntity identification = new IdentificationEntity(1L, "Peter Huber", new Date(1435667215), 10, company);
        em.persist(identification);
        em.getTransaction().commit();

        em.getTransaction().begin();
        IdentificationEntity identification2 = em.find(IdentificationEntity.class, 1L);
        em.getTransaction().commit();
        assertNotNull(identification2);
        assertNotNull(identification2.company);
        assertEquals(identification, identification2);

    }
}
