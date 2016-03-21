package controllers;

import model.Company;
import model.Identification;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repository.IdentificationRepository;

import java.util.List;

public class RestController extends Controller {

	IdentificationRepository identificationRepository = new IdentificationRepository();

	@Transactional
    public Result startIdentification() {
		Identification identification = Identification.fromJson(request().body().asJson());
		identification.save();

        return ok();
    }

	@Transactional
    public Result addCompany() {
		Company company = Company.fromJson(request().body().asJson());
		company.save();

        return ok();
    }

	@Transactional
    public Result identifications() {
		List<Identification> identifications = identificationRepository.findAllSorted();
        return ok(Json.toJson(identifications));
    }

}
