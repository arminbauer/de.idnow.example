import helpers.IdentificationSortingHelper;
import models.Company;
import models.Identification;
import org.junit.Test;
import play.api.db.evolutions.ClassLoaderEvolutionsReader;
import play.db.Database;
import play.db.evolutions.Evolutions;
import play.test.FakeApplication;
import play.test.Helpers;
import repositories.CompanyRepository;
import repositories.IdentificationRepository;

import javax.persistence.PersistenceException;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

/**
 * @author Dmitrii Bogdanov
 * Created at 05.08.18
 */
public class IdentificationRepositoryTest {
  @Test
  public void testGetPendingReturnsListInCorrectOrder() {
    final FakeApplication app = Helpers.fakeApplication();
    final Database db = app.injector().instanceOf(Database.class);
    running(testServer(3333, app), () -> {
      Evolutions.applyEvolutions(db, ClassLoaderEvolutionsReader.forPrefix("testdatabase/"));
      final List<Identification> idents = new IdentificationSortingHelper().sortBySla(new IdentificationRepository(new CompanyRepository()).allPending());
      assertEquals(Arrays.asList(2L, 6L, 5L, 3L, 1L, 4L), idents.stream().map(Identification::getId).collect(Collectors.toList()));
      Evolutions.cleanupEvolutions(db);
    });
  }

  @Test
  public void testGetAllReturnsListInCreationOrder() {
    final FakeApplication app = Helpers.fakeApplication();
    final Database db = app.injector().instanceOf(Database.class);
    running(testServer(3333, app), () -> {
      Evolutions.applyEvolutions(db, ClassLoaderEvolutionsReader.forPrefix("testdatabase/"));
      final List<Identification> idents = new IdentificationRepository(new CompanyRepository()).all();
      assertEquals(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L), idents.stream().map(Identification::getId).collect(Collectors.toList()));
      Evolutions.cleanupEvolutions(db);
    });
  }

  @Test(expected = PersistenceException.class)
  public void testCreateIdentificationWithoutNameThrowsPersistenceException() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final CompanyRepository companyRepository = new CompanyRepository();
      final Company company = new Company();
      company.setSlaTimeInSeconds(30);
      company.setSlaPercentage(0.95f);
      company.setCurrentSlaPercentage(0.95f);
      company.setDeleted(false);
      new CompanyRepository().create(company);
      final Identification identification = new Identification();
      identification.setCompany(company);
      identification.setStartedAt(LocalDateTime.now());
      identification.setDeleted(false);
      identification.setPending(true);
      new IdentificationRepository(companyRepository).create(identification);
      assertEquals(new Long(1L), identification.getId());
    });
  }

  @Test
  public void testCreateIdentificationWithCorrectCompanyCreatesIdentification() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final CompanyRepository companyRepository = new CompanyRepository();
      final Company company = new Company();
      company.setSlaTimeInSeconds(30);
      company.setName("default");
      company.setSlaPercentage(0.95f);
      company.setCurrentSlaPercentage(0.95f);
      company.setDeleted(false);
      new CompanyRepository().create(company);
      final Identification identification = new Identification();
      identification.setCompany(company);
      identification.setUsername("John Smith");
      identification.setStartedAt(LocalDateTime.now());
      identification.setDeleted(false);
      identification.setPending(true);
      final IdentificationRepository identificationRepository = new IdentificationRepository(companyRepository);
      identificationRepository.create(identification);
      assertEquals(new Long(1L), identification.getId());
      assertEquals(identification, identificationRepository.getById(identification.getId()));
    });
  }

  @Test(expected = PersistenceException.class)
  public void testCreateIdentificationWithMissingCompanyThrowsEntityNotFoundException() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final CompanyRepository companyRepository = new CompanyRepository();
      final Company company = new Company();
      company.setSlaTimeInSeconds(30);
      company.setName("default");
      company.setSlaPercentage(0.95f);
      company.setCurrentSlaPercentage(0.95f);
      company.setDeleted(false);
      final Identification identification = new Identification();
      identification.setCompany(company);
      identification.setUsername("John Smith");
      identification.setStartedAt(LocalDateTime.now());
      identification.setDeleted(false);
      identification.setPending(true);
      final IdentificationRepository identificationRepository = new IdentificationRepository(companyRepository);
      identificationRepository.create(identification);
    });
  }

  @Test(expected = ValidationException.class)
  public void testCreateIdentificationWithMissingCompanyThrowsPersistenceException() {
    running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> {
      final Identification identification = new Identification();
      identification.setUsername("John Smith");
      identification.setStartedAt(LocalDateTime.now());
      identification.setDeleted(false);
      identification.setPending(true);
      final IdentificationRepository identificationRepository = new IdentificationRepository(new CompanyRepository());
      identificationRepository.create(identification);
    });
  }
}
