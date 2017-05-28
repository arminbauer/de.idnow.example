package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.Json;
import play.mvc.*;
import java.util.*;

import models.*;
import models.storage.*;

public class RestController extends Controller {

  JsonNode identifications = Json.newArray();
  Storage storage = new Storage();

    public Result startIdentification() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();

      int    id          = json.get("id").numberValue().intValue();
      String name        = json.get("name").asText();
      int    startTime   = json.get("time").numberValue().intValue();
      int    waitingTime = json.get("waiting_time").numberValue().intValue();
      int    comapnyId   = json.get("companyid").numberValue().intValue();
      try {
        Identification newIdentification = new Identification(id, name, startTime, waitingTime, comapnyId);
        storage.addIdentification(newIdentification);

        // Company successful added, tell via HTTP 200
        return ok();
      } catch (StorageGenericException e) {
        // return a "422 - UNPROCESSABLE ENTITY" along with the error message
        //in case we cannot process the request
        return status(422, e.getMessage());
      }
    }

    public Result addCompany() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();

      int    id                   = json.get("id").numberValue().intValue();
      String name                 = json.get("name").asText();
      int    slaTime              = json.get("sla_time").numberValue().intValue();
      float  slaPercentage        = json.get("sla_percentage").numberValue().floatValue();
      float  currentSLAPercentage = json.get("current_sla_percentage").numberValue().floatValue();
      try {
        Company newCompany = new Company(id, name, slaTime, slaPercentage);
        newCompany.setCurrentSLAPercentage(currentSLAPercentage);
        storage.addCompany(newCompany);

        // Company successful added, tell via HTTP 200
        return ok();
      } catch (StorageGenericException e) {
        // return a "422 - UNPROCESSABLE ENTITY" along with the error message
        //in case we cannot process the request
        return status(422, e.getMessage());
      }
    }

    public Result init() {

      return ok();
    }

    public Result identifications() {

      ArrayNode jsonIdentifications = Json.newArray();

      for (Identification identification : storage.getSortedIdentifications()) {
        ObjectNode jsonIdentification = Json.newObject();
        jsonIdentification.put("id",           identification.getId());
        jsonIdentification.put("name",         identification.getName());
        jsonIdentification.put("time",         identification.getStartTime());
        jsonIdentification.put("waiting_time", identification.getWaitingTime());
        jsonIdentification.put("companyid",    identification.getCompanyId());

        jsonIdentifications.add(jsonIdentification);
      }

      return ok(jsonIdentifications);
    }

}
