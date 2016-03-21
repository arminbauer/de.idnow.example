package model;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import errorhandling.NotFoundException;
import org.junit.Test;
import play.db.jpa.JPA;
import repository.CompanyRepository;

import javax.persistence.PersistenceException;
import javax.validation.ValidationException;

public class IdentificationTest {

	private static final int PORT = 3333;

	private CompanyRepository companyRepositoryMock = mock(CompanyRepository.class);

	@Test
	public void testIdNull() {
		Identification identification = new IdentificationBuilder(null).build();
		assertValidationException(identification, "id field must be greater then zero");
	}

	@Test
	public void testIdZero() {
		Identification identification = new IdentificationBuilder(0L).build();
		assertValidationException(identification, "id field must be greater then zero");
	}

	@Test
	public void testNameNull() {
		Identification identification = new IdentificationBuilder().withName(null).build();
		assertValidationException(identification, "name field cannot be empty");
	}

	@Test
	public void testNameEmpty() {
		Identification identification = new IdentificationBuilder().withName("").build();
		assertValidationException(identification, "name field cannot be empty");
	}

	@Test
	public void testTimeNull() {
		Identification identification = new IdentificationBuilder().withTime(null).build();
		assertValidationException(identification, "time field must be non negative");
	}

	@Test
	public void testTimeNegative() {
		Identification identification = new IdentificationBuilder().withTime(-4L).build();
		assertValidationException(identification, "time field must be non negative");
	}

	@Test
	public void testWaitingTimeNull() {
		Identification identification = new IdentificationBuilder().withWaitingTime(null).build();
		assertValidationException(identification, "waiting_time field must be non negative");
	}

	@Test
	public void testWaitingTimeNegative() {
		Identification identification = new IdentificationBuilder().withWaitingTime(-4).build();
		assertValidationException(identification, "waiting_time field must be non negative");
	}

	@Test
	public void testWaitingCompanyIdNull() {
		Identification identification = new IdentificationBuilder().withCompanyId(null).build();
		assertValidationException(identification, "company_id field must be greater then zero");
	}

	@Test
	public void testCompanyIdZero() {
		Identification identification = new IdentificationBuilder().withCompanyId(0L).build();
		assertValidationException(identification, "company_id field must be greater then zero ");
	}

	@Test
	public void testCompanyNotExists() {

		when(companyRepositoryMock.exists(3L)).thenReturn(false);

		Identification identification = new IdentificationBuilder()
				.withCompanyId(3L)
				.withCompanyRepository(companyRepositoryMock)
				.build();

		assertException(identification, NotFoundException.class, "No company found with id 3");
	}

	@Test
	public void testValidObject() {

		when(companyRepositoryMock.exists(1L)).thenReturn(true);

		Identification identification = new IdentificationBuilder()
				.withCompanyId(1L)
				.withCompanyRepository(companyRepositoryMock)
				.build();

		identification.validate();
	}

	@Test
	public void testGetCompanyWhenNull() {
		Company company = new CompanyBuilder().build();

		when(companyRepositoryMock.findById(1L)).thenReturn(company);

		Identification identification = new IdentificationBuilder()
				.withCompanyId(1L)
				.withCompany(null)
				.withCompanyRepository(companyRepositoryMock)
				.build();

		assertThat(identification.getCompany()).isEqualTo(company);
		verify(companyRepositoryMock, times(1)).findById(1L);
	}

	@Test
	public void testGetCompanyWhenSet() {
		Company company = new CompanyBuilder().build();

		when(companyRepositoryMock.findById(1L)).thenReturn(company);

		Identification identification = new IdentificationBuilder()
				.withCompanyId(1L)
				.withCompany(company)
				.withCompanyRepository(companyRepositoryMock)
				.build();

		assertThat(identification.getCompany()).isEqualTo(company);
		verifyZeroInteractions(companyRepositoryMock);
	}

	@Test
	public void testSaveInvalidIdent() {
		running(testServer(PORT, fakeApplication(inMemoryDatabase())), () -> {
			JPA.withTransaction(() -> {
				Company company = new CompanyBuilder(2L).build();
				company.save();

				Identification identification = new IdentificationBuilder(null)
						.withCompany(company)
						.withCompanyId(2L)
						.build();
				try {
					identification.save();
				} catch (Exception e) {
					assertThat(e.getClass().equals(PersistenceException.class));
				}
			});
		});
	}

	@Test
	public void testSaveNoCompany() {
		running(testServer(PORT, fakeApplication(inMemoryDatabase())), () -> {
			JPA.withTransaction(() -> {
				Identification identification = new IdentificationBuilder(null)
						.withCompany(null)
						.withCompanyId(2L)
						.withCompanyRepository(new CompanyRepository())
						.build();
				try {
					identification.save();
				} catch (Exception e) {
					assertThat(e.getClass().equals(NotFoundException.class));
				}
			});
		});
	}

	private void assertValidationException(Identification identification, String message) {
		assertException(identification, ValidationException.class, message);
	}

	private void assertException(Identification identification, Class c, String message) {
		try {
			identification.validate();
		} catch (Exception e) {
			assertThat(e.getClass().equals(c));
			assertThat(e.getMessage()).contains(message);
		}
	}
}