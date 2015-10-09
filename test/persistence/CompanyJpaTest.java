package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class CompanyJpaTest extends AbstractJpaTest{

	 @Test
	 public void test() {
	 em.getTransaction().begin();
	 CompanyEntity company=new CompanyEntity(1L,"Test Bank",60,0.9,0.95);
	 em.persist(company);
	 em.getTransaction().commit();
	
	 em.getTransaction().begin();
	 CompanyEntity company2=em.find(CompanyEntity.class, 1L);
	 em.getTransaction().commit();
	 assertNotNull(company2);
	 assertEquals(company,company2);
	
	 }
	
	//Can also be done this way but not pretty
	/*@Test
	public void persistCompany() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())),
				new Runnable() {
					@Override
					public void run() {

						Callback0 callback = new Callback0() {
							@Override
							public void invoke() throws Throwable {
								persist();
							}

							private void persist() {
								CompanyEntity company = new CompanyEntity(1L,
										"Test Bank", 60, 0.9, 0.95);
								JPA.em().persist(company);
								CompanyEntity company2 = JPA.em().find(
										CompanyEntity.class, 1L);
								assertNotNull(company2);
								assertEquals(company, company2);
							}
						};
						JPA.withTransaction(callback);
					}
				});
	}*/

}
