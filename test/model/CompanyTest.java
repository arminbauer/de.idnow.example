package model;

import static com.google.common.truth.Truth.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.Test;
import play.db.jpa.JPA;

import javax.persistence.PersistenceException;
import javax.validation.ValidationException;

public class CompanyTest {

	private static final int PORT = 3333;

	//region Validation tests
	@Test
	public void testIdNotProvided() {
		Company company = new CompanyBuilder(null).build();
		assertValidationException(company, "id field must be greater then zero");
	}

	@Test
	public void testIdZero() {
		Company company = new CompanyBuilder(0L).build();
		assertValidationException(company, "id field must be greater then zero");
	}

	@Test
	public void testNameValidationNull() {
		Company company = new CompanyBuilder().withName(null).build();
		assertValidationException(company, "name field cannot be empty");
	}

	@Test
	public void testNameValidationEmpty() {
		Company company = new CompanyBuilder().withName("").build();
		assertValidationException(company, "name field cannot be empty");
	}

	@Test
	public void testSlaTimeNull() {
		Company company = new CompanyBuilder().withSlaTime(null).build();
		assertValidationException(company, "sla_time field must be non negative");
	}

	@Test
	public void testSlaTimeNegative() {
		Company company = new CompanyBuilder().withSlaTime(-1).build();
		assertValidationException(company, "sla_time field must be non negative");
	}

	@Test
	public void testSlaPercentageTimeNull() {
		Company company = new CompanyBuilder().withSlaPercentage(null).build();
		assertValidationException(company, "sla_percentage field must be non negative");
	}

	@Test
	public void testSlaPercentageNegative() {
		Company company = new CompanyBuilder().withSlaPercentage(-1.1).build();
		assertValidationException(company, "sla_percentage field must be non negative");
	}

	@Test
	public void testCurrentSlaPercentageTimeNull() {
		Company company = new CompanyBuilder().withCurrentSlaPercentage(null).build();
		assertValidationException(company, "current_sla_percentage field must be non negative");
	}

	@Test
	public void testCurrentSlaPercentageNegative() {
		Company company = new CompanyBuilder().withCurrentSlaPercentage(-0.2).build();
		assertValidationException(company, "current_sla_percentage field must be non negative");
	}

	@Test
	public void testValidObject() {
		Company company = new CompanyBuilder().build();
		company.validate();
	}

	@Test
	public void testSaveInvalidCompany() {
		running(testServer(PORT, fakeApplication(inMemoryDatabase())), () -> {
			JPA.withTransaction(() -> {
				Company company = new CompanyBuilder(null).build();
				try {
					company.save();
				} catch (Exception e) {
					assertThat(e.getClass().equals(PersistenceException.class));
				}
			});
		});
	}

	//endregion

	@Test
	public void testGetCompany() {

	}

	private void assertValidationException(Company company, String message) {
		try {
			company.validate();
		} catch (ValidationException e) {
			assertThat(e.getMessage()).contains(message);
		}
	}
}