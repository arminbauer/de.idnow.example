package controllers;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Company;
import models.Identification;
import models.PriorityCalculator;
import models.Repository;
import models.RepositoryException;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Controller for identifications 
 * 
 * @author leonardo.cruz
 *
 */
public class IdentificationController extends Controller {

	@Inject
	private Repository repository;

    public Result startIdentification() {

    	try {
			
			JsonNode json = request().body().asJson();
			repository.create((Identification) Json.fromJson(json, Identification.class));
			
		} catch (RepositoryException e) {
			
			ObjectNode result = Json.newObject();
			result.put("message", e.getMessage());
			return badRequest(result);

		}
		
		return ok();		

    }

    public Result identifications() {
        //Get the current identification
        //Compute correct order
        //Create new identification JSON with JsonNode identification = Json.newObject();
        //Add identification to identifications list
    	
    	Map<Long,Identification> priorityMap = repository.getIdentification()
    			.collect(Collectors.toMap(
    					this::calculatePriority, 
    					i -> i,
    					(v1,v2) -> v1,TreeMap::new));

        return ok(Json.toJson(priorityMap.values()
        		.stream()
        		.collect(Collectors.toList())));
    }
    
    private Long calculatePriority(Identification identification) {
    	Company company = repository.getCompany(identification.getCompanyId()).get();
    	return new PriorityCalculator(identification,company).getPriority();
    }

}
