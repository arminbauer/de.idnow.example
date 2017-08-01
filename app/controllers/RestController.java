package controllers;

import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import manager.IdentificationManagerImpl;
import mapper.IdentificationMapper;
import models.Company;
import models.Identification;
import models.IdentificationDTO;
import play.Logger;
import play.Logger.ALogger;

import play.libs.Json;
import play.mvc.*;

/**
 * Controller for REST requests.
 *
 * @author Markus Panholzer <markus.panholzer@eforce21.com>
 * @since 31.09.2017
 */
public class RestController extends Controller {

	/** The logger. */
	private static final ALogger log = Logger.of(RestController.class);

	/** Identification manager which holds the identifications. */
	@Inject
	private IdentificationManagerImpl manager;

	/**
	 * REST handler to start a new identification.
	 *
	 * @return Result the http response message.
	 */
	public Result startIdentification() {
		// Get the parsed JSON data
		JsonNode json = request().body().asJson();
		log.debug("POST /api/v1/startIdentification - input: " + json);

		if (json == null) {
			return badRequest("Json was null.");
		}

		try {
			IdentificationDTO identification = Json.fromJson(json, IdentificationDTO.class);
			Identification request = IdentificationMapper.map(identification);
			if (request != null) {
				// Add request to request list
				manager.addIdentificationRequest(request);
			} else {
				return internalServerError("Company id does not exist.");
			}

		} catch (Exception e) {
			log.error("An error occured while starting identification: " + e);
			return internalServerError("Error while proccessing identification data.");
		}
		log.debug("RETURN OK" + json);
		return ok();
	}

	/**
	 * REST handler to add a new company.
	 *
	 * @return Result the http response message.
	 */
	public Result addCompany() {
		// Get the parsed JSON data
		JsonNode json = request().body().asJson();
		log.debug("POST /api/v1/addCompany - input: " + json);

		if (json == null) {
			return badRequest("Json was null.");
		}

		try {
			Company company = Json.fromJson(json, Company.class);
			company.save();

		} catch (Exception e) {
			log.error("An error occured while adding a company: " + e);
			return badRequest();
		}
		return ok();
	}

	/**
	 * REST handler to retrieve all pending identifications ordered by their
	 * priority.
	 *
	 * @return Result the http response message.
	 */
	public Result pendingIdentification() {
		log.debug("GET /api/v1/pendingIdentifications");

		// Get sorted identification list
		List<IdentificationDTO> identifications = manager.getIdentificationList();

		if (identifications == null) {
			return internalServerError();
		}
		return ok(Json.toJson(identifications));
	}

}
