package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import controllers.api.IdNowAPI;
import models.company.CompanyRepository;
import models.company.jsons.CompanyJson;
import models.identification.IdentificationRepository;
import models.identification.jsons.IdentificationJson;
import models.store.IdNowStoreRepository;
import play.libs.Json;
import play.mvc.*;

import javax.inject.Inject;
import java.util.List;

public class RestController extends Controller implements IdNowAPI<Result> {
    private static final String INVALID_JSON_FORMAT = "The json object provided is invalid.";
    private static final String DUPLICATED_COMPANY_ERR = "Duplicated Company or the Id of company is duplicated.";
    private static final String DUPLICATED_IDENTIFICATION_ERR = "Duplicated identification or the Id of company is duplicated.";

    private final CompanyRepository companyRepository;
    private final IdentificationRepository identificationRepository;

    @Inject
    public RestController(CompanyRepository companyRepository, IdentificationRepository identificationRepository) {
        this.companyRepository = companyRepository;
        this.identificationRepository = identificationRepository;
    }

    @Override
    public Result startIdentification() {
    	JsonNode json = request().body().asJson();
        IdentificationJson identificationJson;
        try {
            identificationJson = Json.fromJson(json, IdentificationJson.class);
        } catch (Throwable e) {
            return badRequest(INVALID_JSON_FORMAT);
        }

        try {
            identificationJson = identificationRepository.addIdentification(identificationJson);
        } catch (IdNowStoreRepository.CompanyNotExist companyNotExist) {
            return badRequest(companyNotExist.getMessage());
        } catch (IdNowStoreRepository.DuplicatedElementException e) {
            return badRequest(DUPLICATED_IDENTIFICATION_ERR);
        }

        return ok(Json.toJson(identificationJson));
    }

    @Override
    public Result addCompany() {
    	JsonNode json = request().body().asJson();

        CompanyJson companyJson;

        try {
            companyJson = Json.fromJson(json, CompanyJson.class);
        } catch (Throwable e) {
            return badRequest(INVALID_JSON_FORMAT);
        }

        try {
            companyJson = companyRepository.addCompany(companyJson);
        } catch (IdNowStoreRepository.DuplicatedElementException e) {
            return badRequest(DUPLICATED_COMPANY_ERR);
        }
        return ok(Json.toJson(companyJson));
    }

    @Override
    public Result identifications() {
        List<IdentificationJson> rs = identificationRepository.getSortedIdentificationList();
    	
        return ok(Json.toJson(rs));
    }

    public Result reset() {
        companyRepository.removeAllData();
        return ok();
    }

}
