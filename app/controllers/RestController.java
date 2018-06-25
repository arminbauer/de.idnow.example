package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.node.ArrayNode;
import objects.Company;
import objects.Identification;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import play.twirl.api.Content;
import views.html.display;
import views.html.iden;

import javax.inject.Inject;
import java.util.*;

public class RestController extends Controller {

    private final List<Identification> identifications = new ArrayList<>();
    private final Map<Long, Company> companies = new HashMap<>();

    public Result startIdentification() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	if (json == null){
    	    return badRequest("No JSON data found!");
        }
    	//Do something with the identification
        //Get current Identification and add to current list of open identifications
        Identification identity = null;
    	try{
    	    identity = Json.fromJson(json, Identification.class);
        } catch (Exception e) {
    	    return badRequest("INVALID JSON: " + e.getMessage());
        }
    	Company company = companies.get(identity.getCompanyId());
    	if (company == null){
    	    return badRequest(identity.getCompanyId() + "Company not found");
    	}
    	identity.setCompany(company);
    	identifications.add(identity);
        return ok();
    }

    public Result addCompany() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	if (json == null) {
    	    return badRequest("No JSON data found!");
        }
    	//Do something with the company
        //Get company and add it to current companies
    	Company company =  null;
    	try{
    	    company = Json.fromJson(json, Company.class);
        } catch (Exception e) {
    	    return badRequest("INVALID JSON: " + e.getMessage());
        }
        if (companies.containsKey(company.getId())) {
            return badRequest(company.getId() +  "Company already added");
        }
        companies.put(company.getId(), company);
        return ok();
    }

    public Result identifications() {
    	JsonNode identifications = Json.newArray();
    	
    	//Get the current identification
        List<Identification> idents = new ArrayList<>(getIdentifications());
    	//Compute correct order
        idents.stream().sorted(Identification::compareTo).forEach(i -> ((ArrayNode) identifications).add(Json.toJson(idents)));
    	//Create new identification JSON with JsonNode identification = Json.newObject();
    	//Add identification to identifications list 
    	
        return ok(identifications);
    }

    public Company getCompany(long id) {
        return companies.get(id);
    }

    public Collection<Identification> getIdentifications() {
        return identifications;
    }

    public Result createFormIdentifications(){
        Form<Identification> idenForm = Form.form(Identification.class);
        return ok(iden.render(idenForm));
    }

    public Result addIdentifications(){
        Form<Identification> idenForm = Form.form(Identification.class).bindFromRequest();
        Identification identification = idenForm.get();
        identifications.add(identification);
        return ok();
    }

    public Result screenIdentifications(){
        List<Identification> iden = new ArrayList<>(getIdentifications());
        return ok(display.render(iden));
    }
}
