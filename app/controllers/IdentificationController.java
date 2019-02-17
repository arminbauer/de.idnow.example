package controllers;

import api.Identification;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import play.libs.Json;
import play.mvc.Result;
import service.identification.IdentificationsService;
import service.identification.IdentificatorValidator;

import javax.inject.Inject;
import java.util.List;

import static play.mvc.Controller.request;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

/**
 * Identification REST controller
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class IdentificationController {
    private IdentificationsService identificationsService;
    private IdentificatorValidator identificatorValidator;

    @Inject
    public IdentificationController(IdentificationsService identificationsService, IdentificatorValidator identificatorValidator) {
        this.identificationsService = identificationsService;
        this.identificatorValidator = identificatorValidator;
    }

    public Result startIdentification() {
        JsonNode json = request().body().asJson();

        if (json == null) {
            return badRequest("Identification json isn't presented");
        }
        try {
            Identification identification = Json.fromJson(json, Identification.class);
            List<String> validationErrors = identificatorValidator.validate(identification);
            if (validationErrors.size() > 0) {
                return badRequest(String.join(" ", validationErrors));
            }

            if (identificationsService.addNewIdentification(identification)) {
                return ok("Identification was successfully added");
            }
            return badRequest("Identification already exists");

        } catch (Exception ex) {
            return badRequest("Incorrect json was provided. " + ex.getMessage());
        }
    }

    public Result identifications() {
        ArrayNode identifications = Json.newArray();

        identificationsService.getSortedIdentifications()
                .forEach(identification ->
                        identifications.add(Json.toJson(identification)));

        return ok(identifications);
    }
}
