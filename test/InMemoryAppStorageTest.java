import controllers.core.DuplicatedEntityException;
import controllers.core.EntityNotFoundException;
import controllers.core.InMemoryAppStorage;
import models.Company;
import models.Identification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class InMemoryAppStorageTest {

    private InMemoryAppStorage appStorage;

    @Before
    public void setup() {
        appStorage = InMemoryAppStorage.getInstance();
        appStorage.clear();
    }

    @Test
    public void testGetMissingCompany() {
        Assert.assertNull(appStorage.getCompany(1L));
    }

    @Test
    public void testAddCompany() throws DuplicatedEntityException {
        Company c1 = new Company();
        c1.setId(1L);
        appStorage.addCompany(c1);

        Company c2 = appStorage.getCompany(1L);
        assertSame(c1, c2);
    }


    @Test(expected = DuplicatedEntityException.class)
    public void testAddDuplicatedCompany() throws DuplicatedEntityException {
        Company c1 = new Company();
        c1.setId(1L);
        appStorage.addCompany(c1);

        Company c2 = new Company();
        c2.setId(1L);
        appStorage.addCompany(c2);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testStartIdentificationMissingCompany() throws DuplicatedEntityException, EntityNotFoundException {
        Identification i = new Identification();
        i.setCompanyId(1L);
        appStorage.startIdentification(i);
    }

    @Test
    public void testStartIdentification() throws DuplicatedEntityException, EntityNotFoundException {
        Company c1 = new Company();
        c1.setId(1L);
        appStorage.addCompany(c1);

        Identification i = new Identification();
        i.setCompanyId(c1.getId());

        appStorage.startIdentification(i);
        assertSame("AppStorage must link ident to company", c1, i.getCompany());

        Collection<Identification> collection = appStorage.getIdentifications();
        assertEquals(1, collection.size());
        assertSame(i, collection.iterator().next());
    }

}
