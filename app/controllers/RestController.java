package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.dtos.CompanyDto;
import models.dtos.IdentificationDto;
import models.repositories.CompaniesRepository;
import models.repositories.IdentificationsRepository;
import models.services.IdentificationsOrderingService;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class RestController extends Controller {
	private final CompaniesRepository companiesRepository;
	private final IdentificationsRepository identificationsRepository;
	private final IdentificationsOrderingService identificationsOrderingService;

	@Inject
	public RestController(
			CompaniesRepository companiesRepository,
			IdentificationsRepository identificationsRepository,
			IdentificationsOrderingService identificationsOrderingService) {

		this.companiesRepository = companiesRepository;
		this.identificationsRepository = identificationsRepository;
		this.identificationsOrderingService = identificationsOrderingService;
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result startIdentification() {
		JsonNode json = request().body().asJson();
		IdentificationDto dto = Json.fromJson(json, IdentificationDto.class);
		identificationsRepository.add(dto);

		return ok();
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result addCompany() {
		JsonNode json = request().body().asJson();
		CompanyDto dto = Json.fromJson(json, CompanyDto.class);
		companiesRepository.add(dto);

		return ok();
	}

	public Result identifications() {
		List<IdentificationDto> orderedIdentifications = identificationsOrderingService.getOrderedIdentifications();
		JsonNode json = Json.toJson(orderedIdentifications);

		return ok(json);
	}
}
