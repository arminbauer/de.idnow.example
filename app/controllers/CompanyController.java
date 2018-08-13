package controllers;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Company;
import models.Repository;
import models.RepositoryException;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Controller for companies 
 * 
 * @author leonardo.cruz
 *
 */
public class CompanyController extends Controller {
	
	@Inject
	private Repository repository;

	public Result addCompany() {
		
		try {
			
			JsonNode json = request().body().asJson();
			repository.create((Company) Json.fromJson(json, Company.class));
			
		} catch (RepositoryException e) {
			
			ObjectNode result = Json.newObject();
			result.put("message", e.getMessage());
			return badRequest(result);

		}
		
		return ok();
	}

}
