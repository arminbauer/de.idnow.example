package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.CompanyManagementService;
import services.IdentificationService;
import services.dto.CompanyDTO;
import services.dto.IdentificationDTO;
import services.exceptions.InvalidCompanyException;

public class RestController extends Controller {


    @Inject
    CompanyManagementService companyManagementService;

    @Inject
    IdentificationService identificationService;

    private static <T> T fromRequestToObject(Class<T> type) {
        return Json.fromJson(request().body().asJson(), type);
    }

    public Result startIdentification() throws InvalidCompanyException {
        //Get the parsed JSON data
        JsonNode json = request().body().asJson();
        //Do something with the identification
        identificationService.startIdentification(fromRequestToObject(IdentificationDTO.class));
        return ok();
    }

    public Result addCompany() throws InvalidCompanyException {
        //Get the parsed JSON data
        //Do something with the company
        CompanyDTO companyDTO = fromRequestToObject(CompanyDTO.class);
        companyManagementService.addCompany(companyDTO);
        return ok();
    }

    public Result identifications() {
        JsonNode identifications = Json.newArray();
        //Get the current identification
        //Compute correct order
        //Create new identification JSON with JsonNode identification = Json.newObject();
        //Add identification to identifications list

        return ok(Json.toJson(identificationService.getPendingIdentifications()));
    }

    public Result getAllCompanies() {
        return ok(Json.toJson(companyManagementService.findAll()));
    }
}
