package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Identification;

import play.libs.Json;
import play.mvc.*;
import play.api.*;
import java.util.*;
import views.html.*;

public class EmployeeController extends Controller {

   public Result index() {
   	session("username","aditya");
    return ok("from EmployeeController");
   }

    public  Result addNewNode() {
    	String username = session("username");
    	List<String> names = Arrays.asList("Sriram","Sitammavaru","Lakshmana swamy","Hanuma");
    	JsonNode json = Json.toJson(names);
        Result jsonResult = ok(json).as("application/json");
    	return jsonResult;
    }

   public  Result FindNewNode(String node){
   	Identification identity = new Identification();
   	identity.SetId("2");
    identity.setCustomerName("Sriram");
   	JsonNode personJson = Json.toJson(identity);
   	Result jsonResult = ok(personJson).as("application/json");
     return jsonResult;
   }



}