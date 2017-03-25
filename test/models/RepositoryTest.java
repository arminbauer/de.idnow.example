package models;


import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import java.util.List;
import play.Logger;
import play.libs.ws.WS;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import models.Company;
import models.Identification;
import models.Repository;
import models.exceptions.CompanyAlreadyExitsWithIdException;
import models.exceptions.CompanyAlreadyExitsWithNameException;
import models.exceptions.CompanyNotFoundWithIdException;
import models.exceptions.IdentificationAlreadyExitsWithIdException;
import models.exceptions.IdentificationAlreadyExitsWithNameException;

public class RepositoryTest {
	
	Repository testRepository = new Repository();
	
	@Before
	public void cleanRepository() {
		testRepository.resetRepository();
		Logger.debug("resetRepository");		
	}

	@Test
	public void simpleCheck() {
		Logger.debug("simpleCheck-start");
		int a = 1 + 1;
		assertEquals(2, a);
		Logger.debug("simpleCheck-finished");
	}

	@Test
	public void addCompany() throws CompanyAlreadyExitsWithNameException, CompanyAlreadyExitsWithIdException {		
		Company company = testRepository.addCompany(1, "1", 60, 0.9f);
		assertEquals(testRepository.getCompanies().size(), 1);
		assertEquals(testRepository.getCompanies().get(0).getId(), company.getId());
		Logger.debug("addCompany-finished");
	}

	@Test
	public void addCompanyInvalidSlaTimeValue() {		
		try {
			testRepository.addCompany(1, "1", -60, 0.9f);
			fail();
		} catch (Exception e) {
			Logger.debug(e.getMessage());
		}
		Logger.debug("addCompanyInvalidSlaTimeValue-finished");
	}

	@Test
	public void addCompanyInvalidSlaPercentageValue() {		
		try {
			testRepository.addCompany(1, "1", 60, -1.0f);
			fail();
		} catch (Exception e) {
			Logger.debug(e.getMessage());
		}
		Logger.debug("addCompanyInvalidSlaPercentageValue-finished");
	}

	@Test
	public void getCompanyById() throws CompanyAlreadyExitsWithNameException, CompanyAlreadyExitsWithIdException {		
		Company company = testRepository.addCompany(1, "1", 60, 0.9f);		
		assertEquals(company, testRepository.getCompanyById(company.getId()));
		Logger.debug("getCompanyById-finished");
	}

	@Test
	public void getCompanyByName() throws CompanyAlreadyExitsWithNameException, CompanyAlreadyExitsWithIdException {		
		Company company = testRepository.addCompany(1, "1", 60, 0.9f);
		assertEquals(company, testRepository.getCompanyByName(company.getName()));
		Logger.debug("getCompanyByName-finished");
	}
	

	@Test
	public void pendingIdentificationsEmpty() throws CompanyNotFoundWithIdException {
		
		List<Identification> pendingIdentificationsList = testRepository.pendingIdentifications();
		assertEquals(pendingIdentificationsList.size(), 0);
		Logger.debug("pendingIdentificationsEmpty-finished");

	}

	@Test
	public void pendingIdentificationsExampleOne() throws CompanyAlreadyExitsWithNameException,
			CompanyNotFoundWithIdException, IdentificationAlreadyExitsWithNameException, CompanyAlreadyExitsWithIdException, IdentificationAlreadyExitsWithIdException {		
		Company company1 = testRepository.addCompany(1, "1", 60, 0.9f);
		company1.setCurrentSlaPercentage(0.95f);

		Identification identification11 = testRepository.startIdentification(11, "1-1", company1.getId());
		identification11.setWaitingTime(30);
		Identification identification12 = testRepository.startIdentification(12, "1-2", company1.getId());
		identification12.setWaitingTime(45);

		List<Identification> pendingIdentificationsList = testRepository.pendingIdentifications();
		assertEquals(identification12, pendingIdentificationsList.get(0));
		assertEquals(identification11, pendingIdentificationsList.get(1));
		Logger.debug("pendingIdentificationsExampleOne-finished");

	}
		
	@Test
	public void pendingIdentificationsExampleTwo() throws CompanyAlreadyExitsWithNameException,
			CompanyNotFoundWithIdException, IdentificationAlreadyExitsWithNameException, CompanyAlreadyExitsWithIdException, IdentificationAlreadyExitsWithIdException {		
		Company company1 = testRepository.addCompany(1, "1", 60, 0.90f);
		company1.setCurrentSlaPercentage(0.95f);
		Company company2 = testRepository.addCompany(2, "2", 60, 0.90f);
		company2.setCurrentSlaPercentage(0.90f);

		Identification identification11 = testRepository.startIdentification(1, "1-1", company1.getId());
		identification11.setWaitingTime(30);

		Identification identification21 = testRepository.startIdentification(2, "2-1", company2.getId());
		identification21.setWaitingTime(30);

		List<Identification> pendingIdentificationsList = testRepository.pendingIdentifications();
		assertEquals(identification21, pendingIdentificationsList.get(0));
		assertEquals(identification11, pendingIdentificationsList.get(1));
		
		Logger.debug("pendingIdentificationsExampleTwo-finished");

	}

	@Test
	public void pendingIdentificationsExampleThree() throws CompanyAlreadyExitsWithNameException,
			CompanyNotFoundWithIdException, IdentificationAlreadyExitsWithNameException, CompanyAlreadyExitsWithIdException, IdentificationAlreadyExitsWithIdException {		
		Company company1 = testRepository.addCompany(1, "1", 60, 0.90f);
		company1.setCurrentSlaPercentage(0.95f);
		Company company2 = testRepository.addCompany(2, "2", 120, 0.80f);
		company2.setCurrentSlaPercentage(0.95f);

		Identification identification11 = testRepository.startIdentification(1, "1-1", company1.getId());
		identification11.setWaitingTime(30);

		Identification identification21 = testRepository.startIdentification(2, "2-1", company2.getId());
		identification21.setWaitingTime(30);

		List<Identification> pendingIdentificationsList = testRepository.pendingIdentifications();
		assertEquals(identification11, pendingIdentificationsList.get(0));
		assertEquals(identification21, pendingIdentificationsList.get(1));
		
		Logger.debug("pendingIdentificationsExampleThree-finished");

	}

	@Test
	public void pendingIdentificationsExampleFour() throws CompanyAlreadyExitsWithNameException,
			CompanyNotFoundWithIdException, IdentificationAlreadyExitsWithNameException, CompanyAlreadyExitsWithIdException, IdentificationAlreadyExitsWithIdException {		
		Company company1 = testRepository.addCompany(1, "1", 60, 0.90f);
		company1.setCurrentSlaPercentage(0.95f);
		Company company2 = testRepository.addCompany(2, "2", 120, 0.80f);
		company2.setCurrentSlaPercentage(0.80f);

		Identification identification11 = testRepository.startIdentification(11, "Peter Huber", company1.getId());
		identification11.setWaitingTime(45);

		Identification identification21 = testRepository.startIdentification(21, "Ralf Huber", company2.getId());
		identification21.setWaitingTime(30);

		List<Identification> pendingIdentificationsList = testRepository.pendingIdentifications();
		
		assertEquals(identification11.getId(), pendingIdentificationsList.get(1).getId());
		assertEquals(identification21.getId(), pendingIdentificationsList.get(0).getId());
		Logger.debug("pendingIdentificationsExampleFour-finished");

	}

}
