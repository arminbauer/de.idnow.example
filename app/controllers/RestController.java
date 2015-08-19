package controllers;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import models.Company;
import models.Identification;
import models.KnownCompanies;
import models.PendingIdentifications;
import models.Persistancy;
import models.StorageFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import sorters.PriorityList;
import sorters.SortBySlaFulfillment;
import sorters.SortbyWaitingTime;
import sorters.Sorting;

public class RestController extends Controller {

	private static Persistancy db = StorageFactory.getPersistancy();
	//this would have to use play Model, but time too short to learn how database/model works with play
	
    public Result startIdentification() {
    	JsonNode json = request().body().asJson();
    	
    	PendingIdentifications pendingIdents = db.fetchPendingIdentifications();
    	Identification ident = Identification.fromJson(json);
    	
    	if (!ident.valid()) {
    		return badRequest(json);
    	}
    	
    	if (!pendingIdents.addIdentification(ident)) {
    		return internalServerError();
    	}
    	
    	db.storePendingIdentifications(pendingIdents); //needed if this were a real db; here its only a reference, which didn't change
    	return ok();
    }

    public Result addCompany() {

    	JsonNode json = request().body().asJson();
    	
    	KnownCompanies knownCompanies = db.fetchKnowCompanies();
    	Company comp = Company.fromJson(json);
    	
    	if (!comp.valid()) {
    		return badRequest(json);
    	}
    	
    	if (!knownCompanies.addCompany(comp)) {
    		return internalServerError();
    	}
    	
    	db.storeKnownCompanies(knownCompanies);
        return ok();
    }

    public Result identifications() {
    	ArrayNode identifications = Json.newArray();
    	
    	//Get the current identification
    	//Compute correct order
    	//Create new identification JSON with JsonNode identification = Json.newObject();
    	//Add identification to identifications list 
    	
    	PendingIdentifications pendingIdents = db.fetchPendingIdentifications();
    	
    	List<Integer> ids = pendingIdents.getAllCompanyIds();
    	List<Company> comps = db.fetchKnowCompanies().withIds(ids); //not interested in companies not in current requests

    	PriorityList priorityList = new PriorityList(pendingIdents, 0); //map of idents with priorities, start off all with prio 0
    	
    	//These strategies are hard coded, but in future could be more configurable
    	Sorting sortingStrategy = new SortBySlaFulfillment(comps); // companies closest to sla fulfillment go to end of list
    	priorityList = sortingStrategy.sort(priorityList); //looks for idents with same prio and sorts those, so initially all
    	
    	//we now have a list with those closest to sla fulfillment at the bottom (or those already fulfilled)
    	//for any with the same sla fulfillment, i.e. with same priority we apply next strategy
    	
    	sortingStrategy = new SortbyWaitingTime();
    	priorityList = sortingStrategy.sort(priorityList);
    	
    	Set<Identification> orderedList = priorityList.toOrderedList(pendingIdents);
    	
    	toJson(identifications, orderedList);    	

        return ok(identifications);
    }

	private void toJson(ArrayNode identifications, Iterable<Identification> orderedList) {
		for (Identification ident: orderedList) {
			identifications.add(ident.toJson());
		}
	}

}
