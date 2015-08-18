package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import controllers.core.AppStorage;
import controllers.core.DuplicatedEntityException;
import controllers.core.EntityNotFoundException;
import controllers.core.InMemoryAppStorage;
import models.Company;
import models.Identification;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestController extends Controller {

    private final AppStorage appStorage = InMemoryAppStorage.getInstance();

    public Result startIdentification() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("Expecting Json data");
        }

        Identification ident;
        try {
            ident = Json.fromJson(json, Identification.class);
        } catch (Exception e) {
            return badRequest("Invalid Json data: " + e.getMessage());
        }

        try {
            appStorage.startIdentification(ident);
        } catch (EntityNotFoundException e) {
            return badRequest("Unknown company Id: " + e.getEntityId());
        }

        return ok();
    }

    public Result addCompany() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("Expecting Json data");
        }

        Company company = null;
        try {
            company = Json.fromJson(json, Company.class);
        } catch (Exception e) {
            return badRequest("Invalid Json data: " + e.getMessage());
        }

        try {
            appStorage.addCompany(company);
        } catch (DuplicatedEntityException e) {
            return badRequest("Company already added: " + e.getEntityId());
        }
        return ok();
    }

    public Result identifications() {
        // Get the current identifications
        // VC: Create local collection copy to sort
        // VC: (not optimal solution, a global pre-sorted collection is better, see controllers.core.InMemoryAppStorage.java:38)
        List<Identification> identifications = new ArrayList<>(appStorage.getIdentifications());

        ArrayNode jsonArray = Json.newArray();

        //Create new identification JSON with JsonNode identification = Json.newObject();
        //Add identification to identifications list
        identifications
                .stream()
                .sorted(Identification::compareTo)
                .forEach(ident -> jsonArray.add(Json.toJson(ident)));

        return ok(jsonArray);
    }

}
