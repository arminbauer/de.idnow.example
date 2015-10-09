package controllers;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import models.CompanyDTO;
import models.IdentificationDTO;

import com.fasterxml.jackson.databind.JsonNode;

import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.*;
import services.CompanyService;
import services.IdentificationService;

public class RestController extends Controller {

	@Inject
	CompanyService companyService;
	
	@Inject
	IdentificationService identificationService;
	
	@Transactional
	public Result startIdentification() {
		// Get the parsed JSON data
		JsonNode json = request().body().asJson();

		IdentificationDTO identification=Json.fromJson(json, IdentificationDTO.class);
		
		identificationService.add(identification);

		return ok();
	}
	
	@Transactional
	public Result addCompany() {
		// Get the parsed JSON data
		JsonNode json = request().body().asJson();

		CompanyDTO company = Json.fromJson(json, CompanyDTO.class);
		companyService.add(company);

		return ok();
	}

	@Transactional(readOnly=true)
	public Result identifications() {
		List<IdentificationDTO> identificationDTOs = identificationService.getAll();
		JsonNode identifications = Json.toJson(identificationDTOs);
		return ok(identifications);
	}

}
