package controllers;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Company;
import models.Identification;
import models.Repository;
import models.exceptions.CompanyAlreadyExitsWithIdException;
import models.exceptions.CompanyNotFoundWithIdException;
import models.exceptions.IdentificationAlreadyExitsWithIdException;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.Logger;

public class RestController extends Controller {

	private static Repository dataRepository;
	static {
		dataRepository = new Repository();
	}

	public Result startIdentification() {
		JsonNode json = request().body().asJson();
		Logger.debug("startIdentification" + json);
		final int id = json.get("id").numberValue().intValue();
		final String name = json.get("name").asText();
		final int waitingTime = json.get("waiting_time").numberValue().intValue();
		final int companyid = json.get("companyid").numberValue().intValue();

		try {
			Identification identification;
			identification = dataRepository.startIdentification(id, name, companyid);
			identification.setWaitingTime(waitingTime);
			ObjectNode result = Json.newObject();
			result.put("id", identification.getId());
			Logger.debug("startIdentification-successfully served with data:" + result);
			return ok(result);
		} catch (CompanyNotFoundWithIdException | IdentificationAlreadyExitsWithIdException e) {
			Logger.error(e.getMessage());
			ObjectNode error = Json.newObject();
			error.put("error", e.getMessage());
			return play.mvc.Results.badRequest(error);
		}

	}

	public Result addCompany() {
		JsonNode json = request().body().asJson();
		Logger.debug("addCompany" + json);
		try {
			final int id = json.get("id").numberValue().intValue();
			final String name = json.get("name").asText();
			final int slaTime = json.get("sla_time").numberValue().intValue();
			final float slaPercentage = json.get("sla_percentage").numberValue().floatValue();
			final float currentSlaPercentage = json.get("current_sla_percentage").numberValue().floatValue();

			Company newCompany = dataRepository.addCompany(id, name, slaTime, slaPercentage);
			newCompany.setCurrentSlaPercentage(currentSlaPercentage);
			ObjectNode result = Json.newObject();
			result.put("id", newCompany.getId());
			Logger.debug("addCompany-successfully served with data:" + result);
			return ok(result);
		} catch (CompanyAlreadyExitsWithIdException e) {
			Logger.error(e.getMessage());
			ObjectNode error = Json.newObject();
			error.put("error", e.getMessage());
			return play.mvc.Results.badRequest(error);
		}
	}

	public Result resetRepository() {
		Logger.debug("resetRepository");
		dataRepository.resetRepository();		
		return ok();
	}

	public Result identifications() {
		Logger.debug("identifications");
		ArrayNode identifications = Json.newArray();
		List<Identification> identificationList;
		try {
			identificationList = dataRepository.pendingIdentifications();
			int index = 0;
			for (Identification identification : identificationList) {
				ObjectNode result = Json.newObject();
				result.put("index", index);
				result.put("id", identification.getId());
				result.put("name", identification.getName());
				result.put("time", identification.getTime());
				result.put("waiting_time", identification.getWaitingTime());
				result.put("companyId", identification.getCompany().getId());
				identifications.add(result);
				index = index + 1;
			}
			Logger.debug("identifications-successfully served with data:" + identifications);
			return ok(identifications);
		} catch (CompanyNotFoundWithIdException e) {
			Logger.error(e.getMessage());
			ObjectNode error = Json.newObject();
			error.put("error", e.getMessage());
			return play.mvc.Results.badRequest(error);
		}
	}

}
