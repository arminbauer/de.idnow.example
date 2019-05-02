package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import model.Company;
import model.Identification;
import model.IdentificationSorter;
import play.libs.Json;
import play.mvc.*;

import java.util.*;

public class RestController extends Controller {
    ObjectMapper objectMapper = new ObjectMapper();
    Map<Integer, Identification> identMap = new HashMap();
    Map<Integer, Company> companyMap = new HashMap<>();
    public Result startIdentification() {
        //Get the parsed JSON data
        JsonNode json = request().body().asJson();

        //Do something with the identification
        if (json == null) {
            return badRequest("Expecting Json data");
        } else {
            try {
                Identification ident = objectMapper.treeToValue(json, Identification.class);
                Company parentCompany = companyMap.get(ident.getCompanyid());
                if (parentCompany == null) {
                    throw new Exception("Cannot reference to Company ID, company doesn't exist");
                } else {
                    ident.setCompany(parentCompany);
                }
                identMap.put(ident.getId(), ident);
                return ok("Identifications: " + identMap);
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
                companyMap.put(comp.getId(), comp);
                return ok("Companies: " + companyMap);
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

        List<Identification> identList = new ArrayList<>(identMap.values());

        // calling custom sort will calculate the score of each identification
        // then sort by the scores
        identList.sort(new IdentificationSorter());

        // write data to result array
        for (Identification id : identList) {
            JsonNode js = objectMapper.valueToTree(id);
            ((ArrayNode) identifications).add(js);
        }

        return ok(identifications);
    }

}
