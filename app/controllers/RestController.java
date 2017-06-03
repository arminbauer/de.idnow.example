package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import identification.Company;
import identification.Identification;
import identification.IdentificationPrioritizer;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class RestController extends Controller {

    // normally this should be handled by IoC container, like Spring.
    private static IdentificationPrioritizer identificationPrioritizer = new IdentificationPrioritizer();
    private IdentificationJsonMapper identificationJsonMapper = new IdentificationJsonMapper();
    private CompanyJsonMapper companyJsonMapper = new CompanyJsonMapper();

    public Result startIdentification() {
    	JsonNode json = request().body().asJson();
        Identification identification = identificationJsonMapper.fromJson(json);
        identificationPrioritizer.add(identification);
    	
        return ok();
    }

    public Result addCompany() {
    	JsonNode json = request().body().asJson();
    	Company company = companyJsonMapper.fromJson(json);
    	identificationPrioritizer.add(company);
    	
        return ok();
    }

    public Result identifications() {
    	ArrayNode identifications = Json.newArray();
        identificationPrioritizer.prioritize()
                .forEach(identification -> identifications.add(identificationJsonMapper.toJson(identification)));
        return ok(identifications);
    }

}
