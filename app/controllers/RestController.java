package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import play.libs.Json;
import play.mvc.*;

public class RestController extends Controller {
	
	private static final ConcurrentHashMap<Integer, Company> companies = new ConcurrentHashMap<>();
	private static final Set<Identification> identifications = ConcurrentHashMap.<Identification>newKeySet(); 

    public Result startIdentification() {
    	//Get the parsed JSON data
    	JsonNode identificationNode = request().body().asJson();
    	Identification identification = parseIdentificationJsonNode(identificationNode);
    	identifications.add(identification);
    	    	
        return ok();
    }

    public Result addCompany() {
    	//Get the parsed JSON data
    	JsonNode companyNode = request().body().asJson();
    	Company company = parseCompanyJsonNode(companyNode);
    	// This check is because get is highly efficient in concurrenthashmap. 
    	if(companies.get(company.getId())==null)
    		companies.putIfAbsent(company.getId(),company);    	  
        return ok();
    }

    public Result identifications() {
    	ArrayNode identificationsNodes = Json.newArray();
    	
    	Stream<Identification> sortedStream = identifications.stream().sorted(new IdentificationComparator());
    	    	
    	Stream<JsonNode> jsonNodeStream = sortedStream.map(this::convertToJsonNode);
    	List<JsonNode> jsonNodeSet = jsonNodeStream.collect(Collectors.toList());
    	for(JsonNode node:jsonNodeSet){
    		identificationsNodes.add(node);
    	}
    	
    	
    	//Get the current identification
    	//Compute correct order
    	//Create new identification JSON with JsonNode identification = Json.newObject();
    	//Add identification to identifications list 
    	
        return ok(identificationsNodes);
    }
    
    private Identification parseIdentificationJsonNode(JsonNode identificationNode){
    	JsonNode nameNode = identificationNode.findValue(IdentificationFieldNames.NAME.getValue());
    	JsonNode idNode = identificationNode.findValue(IdentificationFieldNames.ID.getValue());
    	JsonNode waitingTimeNode = identificationNode.findValue(IdentificationFieldNames.WAITING_TIME.getValue());
    	JsonNode companyIdNode = identificationNode.findValue(IdentificationFieldNames.COMPANY_ID.getValue());
    	validateIdentificationNodes(nameNode,idNode,waitingTimeNode,companyIdNode);
    	String name = nameNode.asText();
    	int id = idNode.asInt();
    	int waitingTime = waitingTimeNode.asInt();
    	int companyId = companyIdNode.asInt();
    	validateIdentificationValues(name,id,waitingTime,companyId);
    	return new Identification(id,name,waitingTime,companies.get(companyId));
    	
    }
    
    private Company parseCompanyJsonNode(JsonNode companyNode){
    	JsonNode nameNode = companyNode.findValue(CompanyFieldNames.NAME.getValue());
    	JsonNode idNode   = companyNode.findValue(CompanyFieldNames.ID.getValue());
    	JsonNode slaTimeNode = companyNode.findValue(CompanyFieldNames.SLA_TIME.getValue());
    	JsonNode slaPercentageNode = companyNode.findValue(CompanyFieldNames.SLA_PERCENTAGE.getValue());
    	JsonNode curSlaPercentageNode = companyNode.findValue(CompanyFieldNames.CURRENT_SLA_PERCENTAGE.getValue());
    	validateCompanyNodes(nameNode,idNode,slaTimeNode,slaPercentageNode,curSlaPercentageNode);
    	String name = nameNode.asText();
    	int id = idNode.asInt();
    	int slaTime = slaTimeNode.asInt();
    	double slaPercentage = slaPercentageNode.asDouble();
    	double curSlaPercentage = curSlaPercentageNode.asDouble();
    	validateCompanyValues(name,id,slaTime,slaPercentage,curSlaPercentage);
    	return new Company(name,id,slaTime,slaPercentage,curSlaPercentage);
    	
    	
    }
    private void validateIdentificationNodes(JsonNode nameNode,JsonNode idNode,
    		JsonNode waitingTimeNode,JsonNode companyIdNode){
    	if(nameNode==null)
    		throw new IllegalArgumentException("identification name node is null");
    	if(idNode==null)
    		throw new IllegalArgumentException("identification id node is null");
    	if(waitingTimeNode==null)
    		throw new IllegalArgumentException("identification waiting time node is null");
    	if(companyIdNode==null)
    		throw new IllegalArgumentException("identification company id node is null");    	    	
    }
    private void validateCompanyNodes(JsonNode nameNode,JsonNode idNode,
    		JsonNode slaTimeNode,JsonNode slaPercentageNode,JsonNode curSlaPercentageNode){
    	if(nameNode==null)
    		throw new IllegalArgumentException("company name node is null");
    	if(idNode==null)
    		throw new IllegalArgumentException("company id node is null");
    	if(slaTimeNode==null)
    		throw new IllegalArgumentException("company slaTime node is null");
    	if(slaPercentageNode==null)
    		throw new IllegalArgumentException("company slaPercentage node is null");
    	if(curSlaPercentageNode==null)
    		throw new IllegalArgumentException("company curSlaPercentage node is null");
    	
    }
    
    private void validateCompanyValues(String name,int id,
    		int slaTime,double slaPercentage,double curSlaPercentage){
    	if("".equals(name))
    		throw new IllegalArgumentException("company name is not in json node");
    	if(id==0)
    		throw new IllegalArgumentException("company id is not in json node");
    	if(slaTime==0)
    		throw new IllegalArgumentException("company slaTime is not in json node");
    	if(Double.compare(slaPercentage, 0.0)==0)
    		throw new IllegalArgumentException("company slaPercentage is not in json node");
    	if(Double.compare(curSlaPercentage, 0.0)==0)
    		throw new IllegalArgumentException("company curSlaPercentage is not in json node");
    	
    }
    
    private void validateIdentificationValues(String name,int id,
    		int waitingTime, int companyId){
    	if("".equals(name))
    		throw new IllegalArgumentException("identification name is not in json node");
    	if(id==0)
    		throw new IllegalArgumentException("identification id is not in json node");
    	if(waitingTime==0)
    		throw new IllegalArgumentException("identification waitingTime is not in json node");
    	if(companyId==0)
    		throw new IllegalArgumentException("identification companyId is not in json node");
    	if(companies.get(companyId)==null)
    		throw new IllegalArgumentException("identification companyId is not known.");
    	
    }
    
    public JsonNode convertToJsonNode(Identification identification){
    	ObjectNode identificationNode = Json.newObject();
    	identificationNode.put("name",identification.getName());
    	identificationNode.put("id",identification.getId());
    	identificationNode.put("waiting_time",identification.getWaitingTime());
    	identificationNode.put("companyid",identification.getCompany().getId());
    	identificationNode.put("time",1435667215);
    	return identificationNode;
    }

}
