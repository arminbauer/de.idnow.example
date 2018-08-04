package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import helpers.JsonArrayHelper;
import models.Company;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.CompanyRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
@SuppressWarnings("unused")
@Singleton
public class CompanyRestController extends Controller {
  private final CompanyRepository companyRepository;

  @Inject
  public CompanyRestController(final CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }

  public Result create() {
    final JsonNode json = request().body().asJson();
    Logger.debug("Creating company: {}", json);
    if (json == null) {
      return badRequest("Provide entity");
    }
    final Company company = Json.fromJson(json, Company.class);
    if (company.getId() != null) {
      company.setId(null);
    }
    if (company.isDeleted()) {
      company.setDeleted(false);
    }
    companyRepository.create(company);
    return ok(Json.toJson(company));
  }

  public Result getById(final long id) {
    Logger.debug("Getting company for id: {}", id);
    return ok(Json.toJson(companyRepository.getById(id)));
  }

  public Result getAll() {
    Logger.debug("Getting all companies");
    return ok(JsonArrayHelper.toJsonArray(companyRepository.all()));
  }
}
