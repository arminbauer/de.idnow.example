package helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controllers.RestController;
import models.Company;
import models.Identification;

public class IdentificationHelper {
	
	private static IdentificationHelper identificationObject;
	
	//Instantiates IdentificationHelper object
	public static IdentificationHelper getObject() {
        if (identificationObject == null) {
            identificationObject = new IdentificationHelper();
        }
        return identificationObject;
    }
	
	
	//Gets all identification for every company 
	public Identification[] getAllIdentificationsByCompanyId(int companyId, List<Identification> identifications) {
		Identification[] temp = new Identification[identifications.size()];
		int i = 0;
		for (Identification ident : identifications) {
			if (companyId == ident.getCompanyId()) {
				temp[i] = ident;
				i++;
			}
		}
		Identification[] companyWiseIdentifications = new Identification[i];
		for (int j = 0; j < i; j++) {
			companyWiseIdentifications[j] = temp[j];
		}
		return companyWiseIdentifications;
	}
	
	//Retrieves of pending identifications after sort based on the conditions
	public List<List<Identification>> getPendingIdentification(Company[] sortedCompanies) {
		List<List<Identification>> pendingIdentifications = new ArrayList<>();
     	for (Company company : sortedCompanies){
     		Identification[] companyWiseIdentifactions = getAllIdentificationsByCompanyId(company.getId(), RestController.getAllIdentifications());
     		if(companyWiseIdentifactions.length > 1) {
     			Arrays.sort(companyWiseIdentifactions);
     		}
     		List<Identification> sortedIdentifications = Arrays.asList(companyWiseIdentifactions);
     		if (!sortedIdentifications.isEmpty()) {
     			pendingIdentifications.add(sortedIdentifications);
     		}
     	}
     	return pendingIdentifications;
	}
}
