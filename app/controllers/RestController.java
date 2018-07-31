package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import model.Company;
import model.Identification;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repository.CompanyRepository;

import java.util.Iterator;

import static repository.IdentificationRepository.addIdentification;
import static repository.IdentificationRepository.getPendingIdentifications;

public class RestController extends Controller {

  public Result startIdentification() {
    JsonNode json = request().body().asJson();
    ObjectMapper mapper = new ObjectMapper();
    try {
      Identification identification = mapper.treeToValue(json, Identification.class);
      addIdentification(identification);
      Logger.info(
          "Added new identification: {} for the company {}",
          identification.getId(),
          identification.getCompanyId());
    } catch (JsonProcessingException exception) {
      Logger.error(
          "Couldn't parse JSON input. Identification object couldn't be built.", exception);
      return status(400, "Invalid JSON body.");
    }

    return ok("Identification entity has been added successfully.");
  }

  public Result addCompany() {
    JsonNode json = request().body().asJson();
    ObjectMapper mapper = new ObjectMapper();
    try {
      Company company = mapper.treeToValue(json, Company.class);
      CompanyRepository.addCompany(company);
      Logger.info("Added new company: {}", company.getId());
    } catch (JsonProcessingException exception) {
      Logger.error("Couldn't parse JSON input. Company object couldn't be built.", exception);
      return status(400, "Invalid JSON body.");
    }

    return ok("Company entity has been added successfully.");
  }

  public Result pendingIdentifications() {
    JsonNode identifications = Json.newArray();
    ObjectMapper mapper = new ObjectMapper();

    Iterator iterator = getPendingIdentifications().iterator();

    while (iterator.hasNext()) {
      JsonNode node = mapper.convertValue(iterator.next(), JsonNode.class);
      ((ArrayNode) identifications).add(node);
    }

    return ok(identifications);
  }
}
