package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.sorter.IdentificationSorter;

import java.util.List;

/**
 * Created by Wolfgang Ostermeier on 09.10.2017.
 *
 * Controller to process REST-Calls and Form-encoded-POSTs
 */
public class RestController extends Controller {

    public Result startIdentification() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
        IdentificationTO identificationTO = Json.fromJson(json, IdentificationTO.class);
        Identification identification = TOMapper.map(identificationTO);

        if(identification.getCompany() == null){
            return badRequest("A company with the specified companyid doesn't exist in the database!");
        }
        try {
            identification.save();
        } catch (Exception e) {
            return badRequest(e.getMessage());
        }
        return ok();
    }

    public Result startFormIdentification() {
        //Get the Form data
        Form<IdentificationTO> identificationTOForm = Form.form(IdentificationTO.class).bindFromRequest();
        if(identificationTOForm.hasErrors()){
            flash("error", "Please correct the errors in the form below.");
            return badRequest(views.html.index.render(identificationTOForm));
        }
        Identification identification = TOMapper.map(identificationTOForm.get());

        if(identification.getCompany() == null){
            flash("error", "There is no Company with your specified company ID in the database.");
            return badRequest(views.html.index.render(identificationTOForm));
        }
        try {
            identification.save();
        } catch (Exception e) {
            flash("error", "An error occurred while saving the Identification Request.");
            return badRequest(views.html.index.render(identificationTOForm));
        }
        flash("success", "The Identification Request was successfully saved into the database.");
        return ok(views.html.index.render(identificationTOForm));
    }

    public Result addCompany() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	CompanyTO companyTO = Json.fromJson(json, CompanyTO.class);
    	Company company = TOMapper.map(companyTO);

        try {
            company.save();
        } catch (Exception e) {
            return badRequest(e.getMessage());
        }
        return ok();
    }

    public Result identifications() throws JsonProcessingException {
    	ArrayNode identifications = Json.newArray();

        //Get the current identifications
        List<Identification> identificationList = Identification.find.fetch("company").findList();

         //Compute correct order
        IdentificationSorter.sortIdentifications(identificationList)
    	//Create new identification JSON with JsonNode identification = Json.newObject();
                .forEach(i -> {
            ObjectNode node = Json.newObject();
            node.put("id", i.getId());
            node.put("name", i.getName());
            node.put("time", i.getTime());
            node.put("waiting_time",  i.getWaitingTime());
            node.put("companyid", i.getCompany().getId());
            //Add identification to identifications list
            identifications.add(node);
        });
    	
        return ok(identifications);
    }

}
