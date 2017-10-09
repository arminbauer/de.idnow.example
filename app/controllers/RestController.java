package controllers;

import TOs.CompanyTO;
import TOs.IdentificationTO;
import TOs.TOMapper;
import com.avaje.ebean.Model;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Company;
import models.Identification;
import play.libs.Json;
import play.mvc.*;
import utils.comparators.IdentificationComparator;
import utils.sorter.IdentificationSorter;

import java.util.List;

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
