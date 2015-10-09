package persistence;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;

import play.db.jpa.JPA;
import play.db.jpa.JPAApi;
import play.test.FakeApplication;
import play.test.Helpers;

public abstract class AbstractJpaTest {
	protected EntityManager em;
	private FakeApplication fakeApplication;

	@Before
	public void setUp() throws Exception {
		fakeApplication = Helpers.fakeApplication();
		Helpers.inMemoryDatabase();
		Helpers.start(fakeApplication);
		JPAApi jpaApi = JPA.createFor("defaultPersistenceUnit");
		em = jpaApi.em("default");
		JPA.bindForSync(em);
	}

	@After
	public void tearDown() {
		if (em != null){
			JPA.bindForSync(null);			
			em.close();
		}
		
		if(fakeApplication!=null)
			Helpers.stop(fakeApplication);
	}

}
