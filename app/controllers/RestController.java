package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import model.Company;
import model.Identification;
import model.IdentificationSorter;
import play.libs.Json;
import play.mvc.*;
import service.company.CompanyService;
import service.identification.IdentificationService;

import javax.inject.Inject;
import java.util.*;

public class RestController extends Controller {
    ObjectMapper objectMapper = new ObjectMapper();
    
    @Inject
    IdentificationService idService;

    @Inject
    CompanyService companyService;

    public Result startIdentification() {
        //Get the parsed JSON data
        JsonNode json = request().body().asJson();

        //Do something with the identification
        if (json == null) {
            return badRequest("Expecting Json data");
        } else {
            try {
                Identification ident = objectMapper.treeToValue(json, Identification.class);
                Company parentCompany = companyService.getCompany(ident.getCompanyid());
                if (parentCompany == null) {
                    throw new Exception("Cannot reference to Company ID, company doesn't exist");
                } else {
                    ident.setCompany(parentCompany);
                }
                if (ident.getWaiting_time() <= 0) {
                    throw new Exception("[Waiting_time] Negative or zero values are not allowed");
                }
                idService.addIdentification(ident);
                return ok("Identifications: " + idService.getIdentMap());
            } catch (Exception ex) {
                return internalServerError(ex.getMessage());
            }
        }
    }

    public Result addCompany() {
        //Get the parsed JSON data
        JsonNode json = request().body().asJson();

        //Do something with the company
        if (json == null) {
            return badRequest("Error parsing the Company data");
        } else {
            try {
                Company comp = objectMapper.treeToValue(json, Company.class);
                if (comp.getSla_percentage() <= 0.0 || comp.getCurrent_sla_percentage() <= 0.0 || comp.getSla_time() <= 0.0) {
                    throw new Exception("[SLA_Percentage|Current_SLA_Percentage|SLA_TIME] Negative or zero values are not allowed");
                }
                companyService.addCompany(comp);
                return ok("Companies: " + companyService.getCompanyMap());
            } catch (Exception ex) {
                return internalServerError(ex.getMessage());
            }
        }
    }

    public Result identifications() {
        JsonNode identifications = Json.newArray();
        //Get the current identification
        //Compute correct order
        //Create new identification JSON with JsonNode identification = Json.newObject();
        //Add identification to identifications list
        List<Identification> sortedList = idService.getSortedIdentList();

        // write data to result array
        for (Identification id : sortedList) {
            JsonNode js = objectMapper.valueToTree(id);
            ((ArrayNode) identifications).add(js);
        }

        return ok(identifications);
    }

}
