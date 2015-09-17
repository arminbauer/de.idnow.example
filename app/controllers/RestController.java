package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import play.libs.Json;
import play.mvc.*;

public class RestController extends Controller {
	
	DataStorage dataSingelton = DataStorage.getDataStorage();
		
	public Result postCompany() {	
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	//convert JSON data
    	Company newCompany;
		try {
			newCompany = Company.getCompanyFactory().createCompanyFromJson(json);
		} catch (DupplicateIdException e) {
			return badRequest("Dupplicate id. All company ids need to be unique.");
		} catch (InvalidJsonDataException e) {
    		return badRequest("Missing data field.");
		}
    	if (newCompany == null) {
    		return badRequest();
    	}    	
    	
    	synchronized (dataSingelton.getCompaniesById()) {
    		dataSingelton.getCompaniesById().put(newCompany.id, newCompany);
    	}
    	
        return ok();
    }
	

    public Result getCompanyData() {
    	ArrayNode companyData= Json.newArray();  
    	synchronized (dataSingelton.getCompaniesById()) {
    		for (Company c : dataSingelton.getCompaniesById().values()) {
    			companyData.add(Json.toJson(c));
    		}			
		}
    	
    	return ok(companyData);
    }
    
    public Result deleteCompanyData() {	
    	synchronized (dataSingelton.getCompaniesById()) {
    		dataSingelton.getCompaniesById().clear();			
		}
    	return deleteIdentificationData();
    }
	
	public Result postIdentification() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	
    	Ident newIdent;
		try {
			newIdent = Ident.getFactory().createIdentFromJson(json);
		} catch (DupplicateIdException e) {
			return badRequest("An id is not unique");
		} catch (InvalidJsonDataException e) {
			return badRequest("Issuing Company of Id is unknown.");
		}
    	
    	synchronized (dataSingelton.getAllIdentsSorted()) {
    		dataSingelton.getUsedIdents().add(newIdent.getId());    	
    		dataSingelton.getAllIdentsSorted().add(newIdent);
    	}
    	
        return ok();
    }
	
	
        
    public Result getIdentifications() {
    	ArrayNode identifications = Json.newArray();
    	synchronized (dataSingelton.getAllIdentsSorted()) {
			for (Ident i: dataSingelton.getAllIdentsSorted()) {
				identifications.add(i.toJson());
			}
		}
    	
        return ok(identifications);
    }
    
    public Result deleteIdentificationData () {
    	synchronized(dataSingelton.getAllIdentsSorted()) {
    		dataSingelton.getAllIdentsSorted().clear();
    		dataSingelton.getUsedIdents().clear();
    	}
    	return ok();
    }

}
