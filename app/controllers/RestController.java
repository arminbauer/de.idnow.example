package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Company;
import models.Identification;
import play.libs.Json;
import play.mvc.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class RestController extends Controller {

    private ObjectMapper objectMapper = new ObjectMapper();

    public Result startIdentification(Long companyId) {
        //Get the parsed JSON data
        final JsonNode json = request().body().asJson();
        Identification identification = new Identification();
        try {
            identification = objectMapper.treeToValue(json, Identification.class);
        } catch (IOException e) {
            badRequest("IO Exception" + e.getMessage());
        }
        Company company = Company.companyFind.byId(companyId);
        if (company != null) {
            identification.setCompany(company);
            identification.save();
        } else {
            return notFound("Company Entity Not found for the company id " + companyId);
        }
        return ok(Json.toJson(identification.getId()));
    }

    public Result addCompany() {
        JsonNode json = request().body().asJson();
        Company company = new Company();
        try {
            company = objectMapper.treeToValue(json, Company.class);
        } catch (IOException e) {
            badRequest("IO Exception" + e.getMessage());
        }
        company.save();
        return ok(Json.toJson(company.getId()));
    }

    public Result identifications() {
        JsonNode identifications = Json.newArray();
        final List<Identification> identificationList = Identification.identificationLongFind.all();
        final List<Identification> sortedList = identificationList.stream().sorted((o1, o2) -> {

            final Float slaDifferenceOne = o1.getCompany().getCurrentSlaPercentage() - o1.getCompany().getSlaPercentage();
            final Float slaDifferenceTwo = o2.getCompany().getCurrentSlaPercentage() - o2.getCompany().getSlaPercentage();

            if (slaDifferenceOne.compareTo(slaDifferenceTwo) != 0) {
                return slaDifferenceOne.compareTo(slaDifferenceTwo);
            } else {
                if (o1.getCompany().getId().compareTo(o2.getCompany().getId()) != 0) {
                    return o1.getCompany().getId().compareTo(o2.getCompany().getId());
                } else {
                    return o2.getWaitingTime().compareTo(o1.getWaitingTime());
                }
            }
        }).collect(Collectors.toList());
        try {
            identifications = Json.parse(objectMapper.writeValueAsString(sortedList));
        } catch (JsonProcessingException e) {
            badRequest("Json Processing Exception" + e.getMessage());
        }
        return ok(identifications);
    }
}
