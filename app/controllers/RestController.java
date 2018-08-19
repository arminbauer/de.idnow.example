package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.Company;
import models.Identification;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.IdentificationService;

import javax.inject.Inject;
import java.util.SortedSet;

public class RestController extends Controller {

    @Inject
    private IdentificationService service;

    public Result startIdentification() {
        //Get the parsed JSON data
        JsonNode json = request().body().asJson();

        Identification identification = Identification.fromJson(json);
        service.addIdentification(identification);

        return ok();
    }

    public Result addCompany() {
        //Get the parsed JSON data
        JsonNode json = request().body().asJson();

        Company company = Company.fromJson(json);
        service.addCompany(company);

        return ok();
    }

    public Result identifications() {
        // Get prioritized identifications list
        SortedSet<Identification> priorityList = service.priorityList();

        // Create array of JsonNode
        JsonNode jsonIdents = Json.newArray();
        priorityList.forEach(x -> ((ArrayNode) jsonIdents).add(x.toJson()));

        return ok(jsonIdents);
    }

}
