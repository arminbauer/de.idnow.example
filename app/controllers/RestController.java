package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Company;
import models.Identification;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import util.DBEmulator;
import util.IdentificationService;

import java.util.*;

public class RestController extends Controller {

    public Result startIdentification() {
    	//Get the parsed JSON data
    	//JsonNode json = request().body().asJson();

        Identification identification = Form.form(Identification.class).bindFromRequest().bindFromRequest().get();

        identification.setTime(new Date());

        DBEmulator.getInstance().addIdentification(identification);

        //Do something with the identification

        //return ok();
        return redirect(routes.Application.index());
    }

    public Result addCompany() {
    	//Get the parsed JSON data
    	//JsonNode json = request().body().asJson();
    	
    	//Do something with the company

        Company company = Form.form(Company.class).bindFromRequest().bindFromRequest().get();

        DBEmulator.getInstance().addCompany(company);

        //return ok();
        return redirect(routes.Application.index());
    }

    public Result identifications() {
    	JsonNode identifications = Json.newArray();
    	
    	//Get the current identification
    	//Compute correct order
    	//Create new identification JSON with JsonNode identification = Json.newObject();
    	//Add identification to identifications list

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(IdentificationService.getInstance().computeOrder(), JsonNode.class);
    	
        //return ok(identifications);
        //return ok(index.render(node));
        return ok(node);
    }



    public Result getCompanies(){

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(DBEmulator.getInstance().getCompanyList(), JsonNode.class);


        return ok(node);
    }

}
