
package database;


import model.Company;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

public class RepositoryTest {

    @Test
    public void addCompanyTest()
    {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {

                Repository repo = new Repository();
                String name = "Comp1";
                Company company = new Company();

                assertEquals(false, repo.addCompany(company));

                company.setId(1);
                company.setName(name);
                company.setSlaTime(60);
                company.setSlaPercentage((float)0.60);
                company.setCurrentSlaPercentage((float)0.70);

                assertEquals((Integer)1, company.getId());
                assertEquals(name, company.getName());
                assertEquals((Integer)60,company.getSlaTime());


                assertEquals(true, repo.addCompany(company));
                assertEquals(company, repo.getCompanybyID(1));

            }
        });
    }

}