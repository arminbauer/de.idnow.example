package controllers;


import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Company;
import models.Identification;
import play.mvc.*;
import services.IdentificationService;

/**
 * 
 * @author Paule
 * Restcontroller which hanles json requests for the identification process
 */
public class RestController extends Controller {
	
	@Inject
	private IdentificationService identificationService;
	
	public RestController(IdentificationService identificationService) {
		this.identificationService = identificationService;
	}
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Handles json requests to start the identification
	 * POST 
	 * @return the result of the identification
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
    public Result startIdentification() throws JsonParseException, JsonMappingException, IOException {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	
		Identification identification = (Identification) mapper.readValue(json.toString(), Identification.class);

    	identificationService.addIdentification(identification);
    	
        return ok();
    }
    
    /**
     * Handles json reqeust to add a company
     * POST 
     * @return the result
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    public Result addCompany() throws JsonParseException, JsonMappingException, IOException {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	Company company = mapper.readValue(json.toString(), Company.class);
    	identificationService.addCompany(company);
    	
        return ok();
    }
    
    /**
     * Handles request to get a the identification queue 
     * @return the result
     * @throws JsonProcessingException
     */
    public Result identifications() throws JsonProcessingException {
    	List<Identification> sortedIdents = identificationService.getPendingIdentificationsSorted();
    	JsonNode identifications = mapper.valueToTree(sortedIdents);
    	
        return ok(identifications);
    }

}
