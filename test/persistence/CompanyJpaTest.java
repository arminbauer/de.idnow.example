package persistence;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author prasa on 04-02-2018
 * @project de.idnow.example
 */
public class CompanyJpaTest extends AbstractJpaTest {

    @Test
    public void test() {
        em.getTransaction().begin();
        CompanyEntity company = new CompanyEntity(1L, "Test Bank", 60, 0.9, 0.95);
        em.persist(company);
        em.getTransaction().commit();

        em.getTransaction().begin();
        CompanyEntity company2 = em.find(CompanyEntity.class, 1L);
        em.getTransaction().commit();
        assertNotNull(company2);
        assertEquals(company, company2);

    }
}
