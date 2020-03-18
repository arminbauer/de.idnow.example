package controllers;

import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import dto.CompanyDto;
import dto.IdentificationDto;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import service.CompanyNotExistException;
import service.CompanyService;
import service.IdentificationService;

public class RestController extends Controller {

	private @Inject CompanyService companyService;
	private @Inject IdentificationService identificationService;

	@BodyParser.Of(BodyParser.Json.class)
	public Result startIdentification() {
		// Get the parsed JSON data
		final JsonNode json = request().body().asJson();

		// Do something with the identification
		final IdentificationDto identification = Json.fromJson(json, IdentificationDto.class);

		try {
			identificationService.save(identification);
		} catch (CompanyNotExistException e) {
			return badRequest();
		}
		return ok();
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result addCompany() {
		// Get the parsed JSON data
		final JsonNode json = request().body().asJson();

		// Do something with the company
		final CompanyDto company = Json.fromJson(json, CompanyDto.class);

		companyService.save(company);

		return ok();
	}

	public Result identifications() {
		final List<IdentificationDto> identificationDtos = identificationService.loadOrdered();

		return ok(Json.toJson(identificationDtos));
	}

}
