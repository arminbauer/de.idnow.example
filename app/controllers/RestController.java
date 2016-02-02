package controllers;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;

import models.*;
import play.api.data.Forms;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;

import javax.inject.Inject;
import java.text.Normalizer;
import java.util.List;

public class RestController extends Controller {

    @Inject
    private IdentificationService identificationService;

    @Inject
    private IdentificationHandler identificationHandler;

    @Inject
    private CompanyHandler companyHandler;

    @BodyParser.Of(BodyParser.Json.class)
    public Result startIdentification() {

        JsonNode json = request().body().asJson();
        IdentificationRequest identificationRequest = Json.fromJson(json, IdentificationRequest.class);
        return identificationToOpenList(identificationRequest);
    }

    public Result startIdentificationForm() {
        IdentificationRequest identificationRequest = Form.form(IdentificationRequest.class).bindFromRequest().get();
        return identificationToOpenList(identificationRequest);
    }

    public Result identifications() {
        List<Identification> identificationList = identificationService.getIdentifications();
        return ok(Json.toJson(identificationList));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result addCompany() {
        //Get the parsed JSON data
        JsonNode json = request().body().asJson();

        //Get the Companyobject from Json and save it to DB
        AddCompanyRequest addCompanyRequest = Json.fromJson(json, AddCompanyRequest.class);

        Company company = companyHandler.map(addCompanyRequest);
        company.save();
        return ok();
    }

    private Result identificationToOpenList(IdentificationRequest identificationRequest) {

        Identification identification = identificationHandler.map(identificationRequest);
        if (null == identification) {
            return badRequest("No company found for identification.");
        }

        // The ID would normally be set by the database. This implementation assumes that the identification
        // is persisted after the handling by a call center operator. We add the ID manually to meet the
        // specification.
        identification.setId(identificationService.nextIdentificationId());
        identificationService.addIdentification(identification);
        return ok();
    }
}