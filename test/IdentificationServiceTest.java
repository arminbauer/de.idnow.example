import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;

import models.Company;
import models.Identification;
import services.IdentificationService;
import services.ValidationService;


public class IdentificationServiceTest {
	
	
	@Test
	public void getSortedListTest1() {
		
		IdentificationService identificationService = new IdentificationService(new ValidationService());
		//mock companies
		identificationService.addCompany(mockCompany(1L, "n26", 60, 0.9f, 0.95f));
		identificationService.addCompany(mockCompany(1L, "n26", 60, 0.9f, 0.95f));
		
		//mock identifications
		identificationService.addIdentification(mockIdent(1L, "Pete", LocalDateTime.now(), 30, 1L));
		identificationService.addIdentification(mockIdent(2L, "Pol", LocalDateTime.now(), 30, 2L));
		
		List<Identification> sortedList = identificationService.getPendingIdentificationsSorted();
		
		assertEquals("2", sortedList.get(0).getId().toString());
		assertEquals("1", sortedList.get(1).getId().toString());
	}
	
	@Test
	public void getSortedListTest2() {
		IdentificationService identificationService = new IdentificationService(new ValidationService());
		//mock companies
		identificationService.addCompany(mockCompany(1L, "n26", 60, 0.9f, 0.95f));
		identificationService.addCompany(mockCompany(2L, "bank1", 60, 0.9f, 0.9f));
		
		//mock identifications
		identificationService.addIdentification(mockIdent(1L, "Pete", LocalDateTime.now(), 30, 1L));
		identificationService.addIdentification(mockIdent(2L, "Pol", LocalDateTime.now(), 30, 2L));
		
		List<Identification> sortedList = identificationService.getPendingIdentificationsSorted();
		
		assertEquals("2", sortedList.get(0).getId().toString());
		assertEquals("1", sortedList.get(1).getId().toString());
	}
	
	@Test
	public void getSortedListTest3() {
		IdentificationService identificationService = new IdentificationService(new ValidationService());
		//mock companies
		identificationService.addCompany(mockCompany(1L, "n26", 60, 0.9f, 0.95f));
		identificationService.addCompany(mockCompany(2L, "bank1", 120, 0.8f, 0.95f));
		
		//mock identifications
		identificationService.addIdentification(mockIdent(1L, "Pete", LocalDateTime.now(), 30, 1L));
		identificationService.addIdentification(mockIdent(2L, "Pol", LocalDateTime.now(), 30, 2L));
		
		List<Identification> sortedList = identificationService.getPendingIdentificationsSorted();
		
		assertEquals("1", sortedList.get(0).getId().toString());
		assertEquals("2", sortedList.get(1).getId().toString());
	}
	
	@Test
	public void getSortedListTest4() {
		IdentificationService identificationService = new IdentificationService(new ValidationService());
		//mock companies
		identificationService.addCompany(mockCompany(1L, "n26", 60, 0.9f, 0.95f));
		identificationService.addCompany(mockCompany(2L, "bank1", 120, 0.8f, 0.8f));
		
		//mock identifications
		identificationService.addIdentification(mockIdent(1L, "Pete", LocalDateTime.now(), 30, 1L));
		identificationService.addIdentification(mockIdent(2L, "Pol", LocalDateTime.now(), 30, 2L));
		
		List<Identification> sortedList = identificationService.getPendingIdentificationsSorted();
		
		assertEquals("1", sortedList.get(0).getId().toString());
		assertEquals("2", sortedList.get(1).getId().toString());
	}
	
	
	
	private Identification mockIdent(Long id, String name, LocalDateTime time, int waitingTime, Long companyId) {
		Identification identification = new Identification();
		identification.setId(companyId);
		identification.setName(name);
		identification.setTime(time);
		identification.setWaitingTime(waitingTime);
		return identification;
	}

	private Company mockCompany(Long id, String name, int slaTime, float slaPercentage, float currentPercentage) {
		Company company = new Company();
		company.setId(id);
		company.setName(name);
		company.setSlaTime(slaTime);
		company.setSlaPercentage(slaPercentage);
		company.setCurrentSlaPercentage(currentPercentage);
		return company;
	}
	

}
