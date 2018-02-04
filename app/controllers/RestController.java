package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.CompanyDto;
import models.IdentificationDto;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.CompanyService;
import services.IdentificationService;

import javax.inject.Inject;
import java.util.List;

public class RestController extends Controller {

    @Inject
    CompanyService companyService;

    @Inject
    IdentificationService identificationService;

    @Transactional
    public Result startIdentification() {
        // Get the parsed JSON data
        JsonNode json = request().body().asJson();

        IdentificationDto identification = Json.fromJson(json, IdentificationDto.class);

        identificationService.add(identification);

        return ok();
    }

    @Transactional
    public Result addCompany() {
        // Get the parsed JSON data
        JsonNode json = request().body().asJson();

        CompanyDto company = Json.fromJson(json, CompanyDto.class);
        companyService.add(company);

        return ok();
    }

    @Transactional(readOnly = true)
    public Result identifications() {
        List<IdentificationDto> identificationDTOs = identificationService.getAll();
        JsonNode identifications = Json.toJson(identificationDTOs);
        return ok(identifications);
    }
}
