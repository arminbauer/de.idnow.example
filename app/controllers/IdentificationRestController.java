package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import helpers.IdentificationSortingHelper;
import helpers.JsonArrayHelper;
import models.Identification;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.IdentificationRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Instant;

@SuppressWarnings("unused")
@Singleton
public class IdentificationRestController extends Controller {
  private final IdentificationRepository identificationRepository;
  private final IdentificationSortingHelper identificationSortingHelper;

  @Inject
  public IdentificationRestController(final IdentificationRepository identificationRepository, final IdentificationSortingHelper identificationSortingHelper) {
    this.identificationRepository = identificationRepository;
    this.identificationSortingHelper = identificationSortingHelper;
  }

  public Result getAll() {
    return ok(JsonArrayHelper.toJsonArray(identificationRepository.all()));
  }

  public Result getById(final long id) {
    Logger.debug("Getting identification for id: {}", id);
    return ok(Json.toJson(identificationRepository.getById(id)));
  }

  public Result startIdentification() {
    final JsonNode json = request().body().asJson();
    Logger.debug("Start identification for: {}", json);
    final Identification identification = Json.fromJson(json, Identification.class);
    if (identification.getId() != null) {
      identification.setId(null);
    }
    identification.setStartedAt(Instant.now());
    identificationRepository.create(identification);
    return ok(Json.toJson(identification));
  }

  public Result pendingIdentifications() {
    Logger.debug("Getting list of pending identifications");
    return ok(JsonArrayHelper.toJsonArray(identificationSortingHelper.sortBySla(identificationRepository.allPending())));
  }
}
