package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import helpers.CompanyHelpers;
import helpers.IdentificationHelper;
import models.Company;
import models.Identification;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;


public class RestController extends Controller {
	
	private static List<Identification> identifications = new ArrayList<>();
	Identification currentIdentification = null;
	
	
	//Methods for JSON based inputs
	
	//Adds identification to the open identification list
    public Result startIdentification() {
    	JsonNode json = request().body().asJson();
    	if (json == null){
            return badRequest("Please send identification information to start identification");
        }
    	
    	try {
    		currentIdentification = createIdentification(Json.fromJson(json, Identification.class));
	    	Company companyExist = CompanyHelpers.getObject().getCompanyById(currentIdentification.getCompanyId());
	    	if (null != companyExist) {
	    		JsonNode tojson = Json.toJson(currentIdentification);
	    		currentIdentification.setCompany(companyExist);
	    		identifications.add(currentIdentification);
	    		return ok(tojson);
	    		
	    	}
	    	else {
	    		return badRequest("Company not found");
	    	}
    	} catch(Exception e) {
    		return badRequest("Identification request is not unique");
    	}
    }

    //Adds company to the company list
    public Result addCompany() {
    	JsonNode json = request().body().asJson();
    	if (json == null){
            return badRequest("Please send company information to add.");
        }

    	Company company = CompanyHelpers.getObject().createCompany(Json.fromJson(json, Company.class));
    	if (null == company) {
    		return badRequest("Company already exists");
    	}
    	else {
	    	JsonNode tojson = Json.toJson(company);
	        return ok(tojson);
    	}
    }

    //Retrieves all pending identifications based on the conditions
    public Result pendingIdentifications() {
    	Map<Integer, Company> companies = CompanyHelpers.getObject().getAllComapnies();
    	Company[] sortedCompanies = CompanyHelpers.getObject().getSortedCompanies(companies);
    	
     	List<List<Identification>> pendingIdentifications = IdentificationHelper.getObject().getPendingIdentification(sortedCompanies);
     	
    	ObjectMapper mapper = new ObjectMapper();
    	JsonNode json = mapper.convertValue(pendingIdentifications, JsonNode.class);
    	return ok(json);
    }
    
    //Displays all companies in the list
	public Result companies() {
    	Map<Integer, Company> result = CompanyHelpers.getObject().getAllComapnies();
    	
    	if (result.isEmpty()) {
    		return badRequest("No companies exists to display.");
    	}
    	
    	ObjectMapper mapper = new ObjectMapper();
    	 
    	JsonNode companies = mapper.convertValue(result, JsonNode.class);
    	return ok(companies);
    }
	
	public Identification createIdentification(Identification identification) {
		Identification identificationExist = getIdentificationById(identification.getId());
		if (identificationExist == null){
			return identification;
		} else {
			return null;
		}
	}
	
	public static List<Identification> getAllIdentifications() {
		return new ArrayList<Identification>(identifications);
	}
	
	public Identification getIdentificationById(int identificationId) {
		for (Identification ident : identifications) {
			if (ident.getId() == identificationId) {
				return ident;
			}
		}
		return null;
	}

	
	//UI methods
	//Gets company form to add company
	public Result getCompanyForm(){
		 Form<Company> companyForm = Form.form(Company.class);
		 return ok(views.html.addCompany.render(companyForm));
	
	}
	 
	//Adds company to the company list
	public Result addCompanyForm(){
		 Form<Company> companyForm = Form.form(Company.class).bindFromRequest();
		 Company company = companyForm.get();
		 if (null != CompanyHelpers.getObject().createCompany(company)) {
			 return ok(index.render("Company Successfully Added"));
		 }
		 return badRequest("Company Id not unique");
	
	}

	//Gets form for adding identifications
	public Result getIdentificationForm(){
		 Form<Identification> identForm = Form.form(Identification.class);
		 return ok(views.html.addIdentification.render(getAllIdentifications(), identForm));
	
	}
	
	//Adds identification to open identification list
	public Result addIdentification(){
		 Form<Identification> identForm = Form.form(Identification.class).bindFromRequest();
		 Identification identification = identForm.get();
		 Identification identificationExist = getIdentificationById(identification.getId());
		 Company companyExist = CompanyHelpers.getObject().getCompanyById(identification.getCompanyId());
		 if (null != companyExist && null == identificationExist) {
			 identification.setCompany(companyExist);
			 identifications.add(identification);
			 return ok(index.render("Identification Successfully Added"));
		 } 
		 return badRequest("Company does not exist or Identification request is not unique");
	}
	 
	//Retrieves all pending identification based on conditions
	public Result getPendingIdentification() {
	 	Map<Integer, Company> companies = CompanyHelpers.getObject().getAllComapnies();
    	Company[] sortedCompanies = CompanyHelpers.getObject().getSortedCompanies(companies);
     	List<List<Identification>> identifications = IdentificationHelper.getObject().getPendingIdentification(sortedCompanies);
        return ok(views.html.displayPendingId.render(identifications));
    }
}
