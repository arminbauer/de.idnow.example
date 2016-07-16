package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import com.google.inject.Inject;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import service.dto.CompanyDTO;
import service.dto.IdentificationDTO;
import service.ifaces.IIdentificationService;

import java.util.List;
import java.util.UUID;

public class IdentificationController extends Controller {

    @Inject
    IIdentificationService identificationService;

    /**
     * Creates an identification object.
     *
     * @return
     */
    public Result startIdentification() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
        IdentificationDTO identificationDTO = Json.fromJson(json, IdentificationDTO.class);
        // Validate the Data
        Form<IdentificationDTO> form = Form.form(IdentificationDTO.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest("Invalid JSON");
        }
        // Persist the data
        identificationDTO.setId(UUID.randomUUID().toString());
        identificationService.create(identificationDTO);
        return ok(Json.toJson(identificationDTO));
    }

    /**
     * Returns the list of identifications in the same order as returned by the service layer
     *
     * @return
     */
    public Result identifications() {
        List<IdentificationDTO> identifications = identificationService.getAll();
        return ok(Json.toJson(identifications));
    }
}
