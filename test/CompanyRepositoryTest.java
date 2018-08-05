import models.Company;
import org.junit.Test;
import play.api.db.evolutions.ClassLoaderEvolutionsReader;
import play.db.Database;
import play.db.evolutions.Evolutions;
import play.test.FakeApplication;
import play.test.Helpers;
import repositories.CompanyRepository;

import javax.persistence.PersistenceException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

/**
 * @author Dmitrii Bogdanov
 * Created at 05.08.18
 */
public class CompanyRepositoryTest {
  @Test
  public void testGetAllReturnsListInCreationOrder() {
    final FakeApplication app = Helpers.fakeApplication();
    final Database db = app.injector().instanceOf(Database.class);
    running(testServer(3333, app), () -> {
      Evolutions.applyEvolutions(db, ClassLoaderEvolutionsReader.forPrefix("testdatabase/"));
      final List<Company> all = new CompanyRepository().all();
      assertEquals(Arrays.asList(1L, 2L, 3L, 4L), all.stream().map(Company::getId).collect(Collectors.toList()));
      Evolutions.cleanupEvolutions(db);
    });
  }

  @Test
  public void testCreateCompanyCreatesCorrectCompanyWithAllFieldsSet() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final Company company = new Company();
      company.setSlaTimeInSeconds(30);
      company.setName("name");
      company.setSlaPercentage(0.95f);
      company.setCurrentSlaPercentage(0.95f);
      company.setDeleted(false);
      final CompanyRepository companyRepository = new CompanyRepository();
      companyRepository.create(company);
      assertEquals(new Long(1L), company.getId());
      assertEquals(company, companyRepository.getById(company.getId()));
    });
  }

  @Test(expected = PersistenceException.class)
  public void testCreateCompanyWithoutNameThrowsPersistenceException() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final Company company = new Company();
      company.setSlaTimeInSeconds(30);
      company.setSlaPercentage(0.95f);
      company.setCurrentSlaPercentage(0.95f);
      company.setDeleted(false);
      new CompanyRepository().create(company);
    });
  }
}
