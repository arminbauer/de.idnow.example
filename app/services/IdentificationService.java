package services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.common.base.Objects;

import models.Company;
import models.Identification;

/**
 * 
 * @author Paule
 * 
 * Service to handle the identification process
 */
@Singleton
public class IdentificationService {
	
	
	private ValidationService validationService;
	
	@Inject
	public IdentificationService(ValidationService validationService) {
		this.validationService = validationService;
	}
	
	//no dao layer and h2 db for simplification...
	private ArrayList<Identification> openIdentifications = new ArrayList<>();
	
	//no dao layer and h2 db for simplification...
	private ArrayList<Company> companies = new ArrayList<>();
	
	
	/**
	 * adds a identification to the open identifications
	 * @param identification the identification
	 * @return true, if added
	 */
	public void addIdentification(Identification identification) {
		
//		if (validationService.validateIdentification(identification)) {
			openIdentifications.add(identification);
//		}
//		return false;
	}
	
	/**
	 * adds a company to the list of companies
	 * @param company
	 */
	public void addCompany(Company company) {
		//no validation for simplification...
		companies.add(company);
	}
	
	public synchronized List<Identification> getPendingIdentificationsSorted() {
		
		// here in general it would be nice to know which SLA's are available and does the higher sla beat lower sla with broken current_sla
		// i assume here who pays more will be first
		
		openIdentifications.sort((ident1, ident2) -> {
			Company company1 = companies.get(0);
			Company company2 = companies.get(1);
			System.out.println("start");
			
			if (company1.getId().equals(company2.getId())) {
				System.out.println(company1.getId() == company2.getId());
				System.out.println("same company");
				return (ident1.getWaitingTime() > ident2.getWaitingTime()) ? 1 : -1;
			}
			
			if (ident1.getWaitingTime().equals(ident2.getWaitingTime())) {
				System.out.println("same waiting time");
				if (isSameSla(company1.getSlaTime(), company1.getSlaPercentage(), company2.getSlaTime(), company2.getSlaPercentage())) {
					System.out.println("same sla");
					return (company1.getCurrentSlaPercentage() < company2.getCurrentSlaPercentage()) ? 1 : -1;
				}
				
				if (isFirstSlaGreater(company1.getSlaTime(), company1.getSlaPercentage(), company2.getSlaTime(), company2.getSlaPercentage())) {
					System.out.println("differnt sla");
					return 1;
				} else {
					return -1;
				}
			} else if (ident1.getWaitingTime() > ident2.getWaitingTime() 
					&& isFirstSlaGreater(company1.getSlaTime(), company1.getSlaPercentage(), company2.getSlaTime(), company2.getSlaPercentage())) {
				 return 1;
			} else {
				return -1;
			}
			
		});
		return openIdentifications;
		
	}
	
	private boolean isFirstSlaGreater(Integer slaTime, float slaPercentage, Integer slaTime2, float slaPercentage2) {
	//Here i would be good to know which sla agreements are available to cover all cases.. i do the easy here... 
		if (slaTime < slaTime2 && (slaPercentage - slaPercentage2) > 0) {
			return true;
		}	
		return false;
	}
	
	private boolean isSameSla(Integer slaTime, float slaPercentage, Integer slaTime2, float slaPercentage2) {
		return Objects.equal(slaTime, slaTime2) && Math.abs(slaPercentage - slaPercentage2) < 0.0001;
	}
	
	
	
	
	
	
}
