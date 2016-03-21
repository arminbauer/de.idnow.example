package repository;

import static com.google.common.truth.Truth.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import errorhandling.NotFoundException;
import model.Company;
import model.CompanyBuilder;
import org.junit.Test;
import play.db.jpa.JPA;

import java.util.Optional;

public class CompanyRepositoryTest {

	public static final int PORT = 3333;

	@Test
	public void testCompanyExists() {
		running(testServer(PORT, fakeApplication(inMemoryDatabase())), () -> {
			JPA.withTransaction(() -> {
				Company company = new CompanyBuilder(3L).build();
				company.save();

				CompanyRepository companyRepository = new CompanyRepository();
				assertThat(companyRepository.exists(3L)).isTrue();
			});
		});
	}

	@Test
	public void testCompany4NotExists() {
		running(testServer(PORT, fakeApplication(inMemoryDatabase())), () -> {
			JPA.withTransaction(() -> {
				Company company = new CompanyBuilder(3L).build();
				company.save();

				CompanyRepository companyRepository = new CompanyRepository();
				assertThat(companyRepository.exists(4L)).isFalse();
			});
		});
	}

	@Test
	public void testNoCompany1NotExists() {
		running(testServer(PORT, fakeApplication(inMemoryDatabase())), () -> {
			JPA.withTransaction(() -> {
				CompanyRepository companyRepository = new CompanyRepository();
				assertThat(companyRepository.exists(1L)).isFalse();
			});
		});
	}

	@Test
	public void testCompanyFound() {
		running(testServer(PORT, fakeApplication(inMemoryDatabase())), () -> {
			JPA.withTransaction(() -> {
				Company company = new CompanyBuilder(3L).build();
				company.save();

				CompanyRepository companyRepository = new CompanyRepository();
				assertThat(companyRepository.findById(3L)).isEqualTo(company);
			});
		});
	}

	@Test
	public void testCompanyNotFound() {
		running(testServer(PORT, fakeApplication(inMemoryDatabase())), () -> {
			JPA.withTransaction(() -> {
				CompanyRepository companyRepository = new CompanyRepository();
				try {
					companyRepository.findById(3L);
				} catch (Exception e) {
					assertThat(e.getClass().equals(NotFoundException.class));
				}
			});
		});
	}
}