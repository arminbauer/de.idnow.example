package controllers;

import business.company.entity.Company;
import business.identification.entity.Identification;
import com.fasterxml.jackson.databind.JsonNode;
import data.IdentificationTO;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

public class RestController extends Controller {

    @Transactional
    public Result startIdentification() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();

        IdentificationTO identificationTO = Json.fromJson (json, IdentificationTO.class);
        //Do something with the identification

        Identification identification = Identification.fromTO (identificationTO);

        JPA.em ().persist (identification);

    	
        return created ();
    }

    @Transactional
    public Result addCompany() {
    	//Get the parsed JSON data
    	JsonNode json = request().body().asJson();

        Company company = Json.fromJson (json, Company.class);


        JPA.em ().persist (company);

    	
        return created ();
    }

    @Transactional
    public Result identifications() {
    	JsonNode identifications = Json.newArray();
    	
    	//Get the current identification
    	//Compute correct order
    	//Create new identification JSON with JsonNode identification = Json.newObject();
    	//Add identification to identifications list

        Query namedQuery = JPA.em ().createNamedQuery ("Identification.all");
        @SuppressWarnings ("unchecked")
        List<Identification> resultList = namedQuery.getResultList ();


        // sort by waiting time: higher means more urgent
        resultList.sort((i1,i2) -> (i2.getWaitingTime ().compareTo (i1.getWaitingTime ())));

        // sort by company SLA percentage lower means more urgent
        resultList.sort (
                (i1, i2) ->
                        (i1.getCompany ().getSlaPercentage ().compareTo (i2.getCompany ().getSlaPercentage ()))
        );

        // sort by difference between  Current SLA and SLA percentage: lower means urgent
        resultList.sort (
                (i1,i2) ->
                        ( i1.getCompany ().getSLAPercentageUrgency ().compareTo (i2.getCompany ().getSLAPercentageUrgency ()))
        );

        // sort by difference between SLA Time and waiting time: lower means urgent
        resultList.sort (
                (i1,i2) ->
                        ( i1.getSLATimeUrgency ().compareTo (i2.getSLATimeUrgency ()))
        );




        identifications = Json.toJson (resultList.stream ().map(IdentificationTO::fromBO).collect (Collectors.toList ()));
        return ok(identifications);
    }


    @Transactional
    public Result companies() {

        Query namedQuery = JPA.em ().createNamedQuery ("Company.all");

        @SuppressWarnings ("unchecked")
        List<Company> resultList = namedQuery.getResultList ();

        return ok(Json.toJson (resultList));
    }

}
