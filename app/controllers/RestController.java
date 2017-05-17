package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;
import play.mvc.*;
import play.Logger;
import models.Company;
import models.Identification;
import models.State;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import javax.inject.*;
import static java.util.Arrays.sort;

public class RestController extends Controller {

    @Inject State state;

    private final Logger.ALogger logger = Logger.of("apilogger");

    public Result startIdentification() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();

        //Do something with the identification
    	Identification identification = Json.fromJson(json, Identification.class);

    	Company company = state.getCompanies().get((Integer)identification.getCompanyid());

    	if (company == null) {
    	    return Results.badRequest("Unknown company for identification");
        }

    	state.getIdentifications().add(identification);
    	
        return ok();
    }

    public Result addCompany() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();
    	Company company = Json.fromJson(json, Company.class);

        //Do something with the company
    	state.getCompanies().put(company.getId(), company);

        return ok();
    }

    public Result identifications() {
        logger.debug("Current identifications requested");
        /*Identification i = new Identification();
        i.setId(1);
        i.setName("Max Müller");
        i.setTime(System.currentTimeMillis());
        i.setWaiting_time(15);
        i.setCompanyid(1);
        identifications.offer(i);*/

    	//Get the current identification
        Identification[] result = state.getIdentifications().toArray(new Identification[0]);
        final Map<Integer, Company> companies = state.getCompanies();

        Comparator<Identification> comp = Comparator.comparingInt((Identification a) ->  {return a.getWaiting_time();}).reversed() //sort by waiting time (high to low)
                .thenComparingInt((Identification a) -> { //sort by SLA time (low to high)
                    Company aCompany = companies.get((Integer) a.getCompanyid());
                    return aCompany.getSla_time();
                }).thenComparing((Identification a, Identification b) -> { //sort by SLA percentage

                    Company aCompany = companies.get((Integer) a.getCompanyid());
                    Company bCompany = companies.get((Integer) b.getCompanyid());

                    float aPercentageDiff = aCompany.getCurrent_sla_percentage() - aCompany.getSla_percentage();
                    float bPercentageDiff = bCompany.getCurrent_sla_percentage() - bCompany.getSla_percentage();

                    return (aPercentageDiff < bPercentageDiff) ? -1 : 1;
                });

    	//Compute correct order
        logger.debug("Sorting identifications");
        sort(result, comp);

        //JSON result
        logger.debug("Sending JSON result");
        JsonNode json = Json.toJson(result);
        return ok(json);
    }

}
