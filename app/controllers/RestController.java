package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.Company;
import models.Identification;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.IdentificationService;
import services.InMemoryStore;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class RestController extends Controller {

    @Inject
    private InMemoryStore store;
    @Inject
    private IdentificationService identificationService;

    public Result startIdentification() {

        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("JSON body expected.");
        }

        Form<Identification> form = Form.form(Identification.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }

        Identification identification = form.get();
        store.addIdentification(identification);

        return ok("Added identification: " + identification);
    }

    public Result addCompany() {

        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("JSON body expected.");
        }

        Form<Company> form = Form.form(Company.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }

        Company company = form.get();
        store.addCompany(company);

        return ok("Added company: " + company);
    }

    public Result identifications() {
        ArrayNode identifications = Json.newArray();

        //Get the current identification
        //Compute correct order
        //Create new identification JSON with JsonNode identification = Json.newObject();
        //Add identification to identifications list

        List<Identification> prioritizedIdentifications = identificationService.prioritizeAll();

        identifications.addAll(
                prioritizedIdentifications
                        .stream()
                        .map(Json::toJson)
                        .collect(Collectors.toList())
        );

        return ok(identifications);
    }

}
