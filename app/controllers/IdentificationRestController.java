package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.Identification;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.IdentificationService;

import javax.inject.Inject;
import java.util.List;

import static util.json.JsonHelper.parseJson;

public class IdentificationRestController extends Controller {

    @Inject
    private IdentificationService identificationService;

    public Result identifications() {
        // Get the current ordered main
        List<Identification> identifications = identificationService.getIdentificationsOrderBySLA();
        // Serialize main to JSON
        ArrayNode jsonNodes = serializeListOfIdentificationsToJson(identifications);
        // Return ok result
        return ok(jsonNodes);
    }

    public Result startIdentification() {
        // Get the parsed JSON data
        JsonNode json = request().body().asJson();
        // Parse JSON data to Identification instance
        Identification identification = parseJson(json, Identification.class);
        // Save identification
        identificationService.save(identification);
        // Return ok result
        return ok();
    }

    private ArrayNode serializeListOfIdentificationsToJson(List<Identification> identifications) {
        ArrayNode jsonNodes = Json.newArray();
        identifications.forEach(it -> {
            JsonNode jsonNode = Json.toJson(it);
            jsonNodes.add(jsonNode);
        });
        return jsonNodes;
    }
}
