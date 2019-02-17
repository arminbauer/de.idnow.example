package controllers;

import api.WeightSettings;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.WeightSettingsService;

import javax.inject.Inject;

/**
 * Weight settings REST controller
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class WeightSettingsController extends Controller {

    private WeightSettingsService weightSettingsService;

    @Inject
    public WeightSettingsController(WeightSettingsService weightSettingsService) {
        this.weightSettingsService = weightSettingsService;
    }

    public Result addWeightSettings() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("Weight settings json isn't presented");
        }
        try {
            WeightSettings weightSettings = Json.fromJson(json, WeightSettings.class);
            if (weightSettingsService.addWeightSettings(weightSettings)) {
                return ok("Weight settings were successfully added");
            }
            return badRequest("Weight settings already exist");

        } catch (Exception ex) {
            return badRequest("Incorrect json was provided. " + ex.getMessage());
        }
    }
}
