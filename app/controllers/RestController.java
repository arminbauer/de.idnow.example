package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import play.libs.Json;
import play.mvc.*;

import java.util.*;


public class RestController extends Controller {
	
	// -- STATIC FIELDS ---------------------------------------------
	
	public static final int IDENT_NODE_COUNT = 5;
	public static final String IDENT_KEY_ID = "id";
	public static final String IDENT_KEY_COMPANY_ID = "companyid";
	public static final String IDENT_KEY_NAME = "name";
	public static final String IDENT_KEY_TIME = "time";
	public static final String IDENT_KEY_WAITING_TIME = "waiting_time";
	
	public static final int COMP_NODE_COUNT = 5;
	public static final String COMP_KEY_ID = "id";
	public static final String COMP_KEY_NAME = "name";
	public static final String COMP_KEY_SLA_TIME = "sla_time";
	public static final String COMP_KEY_SLA_PERC_LIMIT = "sla_percentage";
	public static final String COMP_KEY_SLA_PERC_CURRENT = "current_sla_percentage";
	
	public static final float MAX_DIFF = 0.05f;	// kinda high, for production a lower value should be chosen
	public static final boolean DEBUG = false;
	
	
	// -- FIELDS ----------------------------------------------------
	
	private ArrayList<Identification> idents;	// queue for the identifications (sorted), index 0 ~ highest priority, index n ~ lowest priority
	private ArrayList<Company> comps;			// list of companies (unsorted)
												// additionally a bidirectional references is established (Company objects know associated Identifications and vice versa)
	
	private boolean FLAG_SORT_COMPANIES = true;
	
	
	public RestController(){
		super(); // doing all the stuff of the superclass
		
		// initializing variables which hold information
		idents = new ArrayList<Identification>();
		comps = new ArrayList<Company>();
		
		if(DEBUG){
			System.out.println("online");
			
			debugAddCompany("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
			debugAddCompany("{\"id\": 2, \"name\": \"Test Bank 2\", \"sla_time\": 60, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.85}");
			
			debugAddCompany("{\"id\": 12, \"name\": \"08/15 Bank\", \"sla_time\": 30, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.82}");
			
			debugAddCompany("{\"id\": 5, \"name\": \"Fast Bank\", \"sla_time\": 20, \"sla_percentage\": 0.4, \"current_sla_percentage\": 0.35}");
			debugAddCompany("{\"id\": 6, \"name\": \"Even Faster Bank\", \"sla_time\": 10, \"sla_percentage\": 0.2, \"current_sla_percentage\": 0.15}");
			
			debugAddCompany("{\"id\": 111, \"name\": \"Siberian Banka\", \"sla_time\": 120, \"sla_percentage\": 0.8, \"current_sla_percentage\": 0.2}");
			
			debugAddCompany("{\"id\": 15, \"name\": \"Pricey Bank\", \"sla_time\": 20, \"sla_percentage\": 0.95, \"current_sla_percentage\": 0.97}");
			
			// ---
			
			debugStartIdentification("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 35, \"companyid\": 1}");
			debugStartIdentification("{\"id\": 2, \"name\": \"Bernd Huber\", \"time\": 1435667215, \"waiting_time\": 25, \"companyid\": 1}");
			debugStartIdentification("{\"id\": 3, \"name\": \"Markus Huber\", \"time\": 1435667215, \"waiting_time\": 20, \"companyid\": 2}");
			debugStartIdentification("{\"id\": 4, \"name\": \"Thomas Huber\", \"time\": 1435667215, \"waiting_time\": 40, \"companyid\": 2}");
			debugStartIdentification("{\"id\": 5, \"name\": \"Erik Huber\", \"time\": 1435667215, \"waiting_time\": 30, \"companyid\": 2}");
			
			debugStartIdentification("{\"id\": 6, \"name\": \"Otto Schmidt\", \"time\": 1435686211, \"waiting_time\": 20, \"companyid\": 12}");
			
			debugStartIdentification("{\"id\": 7, \"name\": \"Bot 1\", \"time\": 1435686211, \"waiting_time\": 24, \"companyid\": 5}");
			debugStartIdentification("{\"id\": 8, \"name\": \"Bot 2\", \"time\": 1435686211, \"waiting_time\": 18, \"companyid\": 5}");
			debugStartIdentification("{\"id\": 9, \"name\": \"Bot 3\", \"time\": 1435686211, \"waiting_time\": 12, \"companyid\": 5}");
			debugStartIdentification("{\"id\": 10, \"name\": \"Bot 4\", \"time\": 1435686211, \"waiting_time\": 15, \"companyid\": 6}");
			debugStartIdentification("{\"id\": 11, \"name\": \"Bot 5\", \"time\": 1435686211, \"waiting_time\": 10, \"companyid\": 6}");
			
			debugStartIdentification("{\"id\": 12, \"name\": \"Vladimir Putin\", \"time\": 1435686211, \"waiting_time\": 240, \"companyid\": 111}");
			debugStartIdentification("{\"id\": 13, \"name\": \"Braunbär 357\", \"time\": 1435686211, \"waiting_time\": 20, \"companyid\": 111}");
		}
	}
		
    public Result startIdentification() {
    	JsonNode json = request().body().asJson();		// Get the parsed JSON data
    	
		// checking for validity
		if(!isValidIdentificationData(json))
			return badRequest();
		
		// checking if company has already been added
		int id = json.get(IDENT_KEY_COMPANY_ID).asInt(-1); 	// default value of -1, in case json-field is corrupt
		Company comp = getCompany(id);
		
		// not adding identifications that have no associated company
		if(comp == null)
			return badRequest();
		
		
		// creating new Identification object and syncing objects
		Identification ident = new Identification(json);
		ident.registerCompany(comp);
		comp.registerIdentification(ident);
		
		idents.add(ident);	// simply adding the instance, sorting is done later

		return ok();
    }

    public Result addCompany() {
    	JsonNode json = request().body().asJson();		// Get the parsed JSON data
    	
		// checking for validity
		if(!isValidCompanyData(json))
			return badRequest();
		
		// adding data if valid
		// in case of a duplicate => refreshing sla_current
		Company newComp = new Company(json);
		Company listComp = getCompany(newComp.getId());

		if(listComp == null){
			comps.add(newComp);
			FLAG_SORT_COMPANIES = true;					// raising flag => new sorting necessary
		}
		
		return ok();
    }

    public Result identifications() {
		if(!DEBUG){
			//sort the queue!
			sortQueue();
			
			//send result as json!
			return ok(identsToJson());
			
        }else{
			sortQueue();
			
			String response = "Indentifications:\n-----------------\n\n";
			String strIdents = identsToJson().toString();
			String strComps = compsToJson().toString();
			
			response += strIdents.substring(1, strIdents.length() - 1).replace("},", "},\n");
			response += "\n\n\n\nCompanies:\n----------\n\n";
			response += strComps.substring(1, strComps.length() - 1).replace("},", "},\n");
			
			return ok(response);
		}
    }
	
	
	// -- PRIVATE METHODS -------------------------------------------
		
	private void sortQueue(){
		/*	HEART
			
			definitions:	 SLA%	== 		   SLA-percentage			e.g. 0.8
							cSLA%	== current SLA-percentage			e.g. 0.9, 0.7, ...
							dSLA%	== cSLA% - SLA%						e.g. 0.9 - 0.8, 0.7 - 0.8, ...
							 SLAt	== 		   SLA-time					e.g. 60
							dSLAt	== SLA-time - waiting time			e.g. 60 - 55, 60 - 80, ...
							 
			aim: 			maintaining condition SLA% <= cSLA%			for all companies
			
			rule 1:			lowest dSLA% ~ highest priority				for identifications of different companies
			
			rule 2:			lowest dSLAt ~ highest priority 			for identifications of the same company OR different companies with equal dSLA%
			
			rule 3:			rule 1 > rule 2
			
			
			algorithm:		1.) sort companies based on rule 1
							2.) sort identifications of a company based on rule 2
							3.) create the list
							
							with	index 0 ~ highest priority
									index n ~ lowest priority
		*/
		
		
		// 1.) sort identifications based on dSLA%
		if(FLAG_SORT_COMPANIES){
			
			// bubble-sorting the companies (lowest to highest dSLA%)
			for(int j = comps.size(); j > 1; j--){
				for(int i = 0, len = comps.size() - 1; i < len; i++){
					if(comps.get(i).getSLAPercentageDelta() > comps.get(i + 1).getSLAPercentageDelta()){
						Company temp = comps.remove(i + 1);
						comps.add(i, temp);
					}
				}
			}
			
			FLAG_SORT_COMPANIES = false;
		}
		
		
		
		// 2.) sort identifications based on dSLAt
		ArrayList<Identification> identset = new ArrayList<Identification>();	// for buffer relevant indentifications
		int identIndex = 0;														// keeps track of the index of the overall list
		
		
		for(int k = 0, klen = comps.size(); k < klen; k++){
			
			identset.addAll(comps.get(k).getAssociatedIdentifications());
			
			// do sorting if 
			//   a) end is reached
			//   b) the dSLA% of the current and the next company is NOT equal
			if(k == klen - 1 || !isEqualFloat(comps.get(k).getSLAPercentageDelta(), comps.get(k + 1).getSLAPercentageDelta(), MAX_DIFF)){
				
				// bubble-sort relevant indentifications (lowest to highest dSLAt)
				for(int j = identset.size(); j > 1; j--){
					for(int i = 0, len = identset.size() - 1; i < len; i++){
						if(identset.get(i).getSLATimeDelta() > identset.get(i + 1).getSLATimeDelta()){
							Identification temp = identset.remove(i + 1);
							identset.add(i, temp);
						}
					}
				}
				
				// 3.) integrating sorted set into the overall indentifications list
				for(Identification ident : identset){
					idents.remove(ident);
					idents.add(identIndex, ident);
					identIndex++;					
				}
				
				// cleaning up for next sorting set
				identset.clear();
			}		
		}
	}
	
	private JsonNode identsToJson(){
		JsonNodeFactory jnf = JsonNodeFactory.instance;
		ArrayNode list = new ArrayNode(jnf);
		
		// filling json-array with the identifications
		for(Identification ident : idents)
			list.add(ident.toJson(jnf));
		
		return list;
	}
	
	private JsonNode compsToJson(){
		JsonNodeFactory jnf = JsonNodeFactory.instance;
		ArrayNode list = new ArrayNode(jnf);
		
		// filling json-array with the companies
		for(Company comp : comps)
			list.add(comp.toJson(jnf));
		
		return list;
	}
	
	private Company getCompany(int company_id){
		// checking each company id
		// returning the company object if its id matches the given one
		
		for(Company c : comps){
			if(c.getId() == company_id){
				return c;			
			}
		}
		
		return null;
	}
	
	private boolean isValidIdentificationData(JsonNode ident){
		// e.g. {\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}
		
		// children count?
		if(ident.size() != IDENT_NODE_COUNT)
			return false;
		
		
		// necessary fields?
		if(!ident.has(IDENT_KEY_ID))
			return false;
		
		if(!ident.has(IDENT_KEY_COMPANY_ID))
			return false;
		
		if(!ident.has(IDENT_KEY_NAME))
			return false;
		
		if(!ident.has(IDENT_KEY_TIME))
			return false;
		
		if(!ident.has(IDENT_KEY_WAITING_TIME))
			return false;
		
		// further filtering possible (datatypes, ranges)
		
		return true;
	}
	
	private boolean isValidCompanyData(JsonNode comp){
		// e.g. {\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}
		
		if(comp.size() != COMP_NODE_COUNT)
			return false;
		
		
		// necessary fields?
		if(!comp.has(COMP_KEY_ID))
			return false;
		
		if(!comp.has(COMP_KEY_NAME))
			return false;
		
		if(!comp.has(COMP_KEY_SLA_TIME))
			return false;
		
		if(!comp.has(COMP_KEY_SLA_PERC_LIMIT))
			return false;
		
		if(!comp.has(COMP_KEY_SLA_PERC_CURRENT))
			return false;
		
		// further filtering possible (datatypes, ranges)
		
		return true;
	}
	
	private boolean isEqualFloat(float f1, float f2, float maxDiff){
		// it is nonsensical to directly compare two floats
		// therefore this method checks the difference, within maxDiff f1 and f2 are regarded as equal
		
		if(Math.abs(f1 - f2) < maxDiff)
			return true;
		else
			return false;	
	}
	
	
	// -- DEBUG METHODS ---------------------------------------------
	
	private void debugStartIdentification(String jsonString){
		JsonNode json = Json.parse(jsonString);
		
		
		//checking for validity
		if(!isValidIdentificationData(json))
			return;
		
		//checking if company has already been added
		int id = json.get(IDENT_KEY_COMPANY_ID).asInt(-1); 		//default value of -1, in case json-field is corrupt
		Company comp = getCompany(id);
		
		// not adding identifications that have no associated company
		if(comp == null){
			System.out.println("no company");
			return;
		}
		
		
		//creating new Identification object and syncing objects
		Identification ident = new Identification(json);
		ident.registerCompany(comp);
		comp.registerIdentification(ident);
		
		idents.add(ident);	// simply adding the instance, sorting is done later
	}
	
	private void debugAddCompany(String jsonString){
		JsonNode json = Json.parse(jsonString);
    	
		// checking for validity
		if(!isValidCompanyData(json))
			return;
		
		// adding data if valid
		// in case of a duplicate => refreshing sla_current
		Company newComp = new Company(json);
		Company listComp = getCompany(newComp.getId());

		if(listComp == null){
			comps.add(newComp);
			FLAG_SORT_COMPANIES = true;					// raising flag => new sorting necessary
		}
	}
	
	
	
	// -- AUXILIARY CLASSES -----------------------------------------
	
	public class Identification {
		private String name;					// The name of the user
		private int id;							// The unique ID of the identification
		private int company_id;					// The ID of the company to which this identification belongs
		
		private long time;						// The time when this identification request was started by the user (Unix format)
		private int waiting_time;				// The current waiting time of the identification in seconds (since the user started)
		
		private Company comp;					// Reference to respective company object => saves time when sorting the list
		
		
		public Identification(JsonNode ident){
			// e.g. {\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}
			
			name 		 = ident.get(IDENT_KEY_NAME).asText();
			id 			 = ident.get(IDENT_KEY_ID).asInt();
			company_id 	 = ident.get(IDENT_KEY_COMPANY_ID).asInt();
			time		 = ident.get(IDENT_KEY_TIME).asLong();
			waiting_time = ident.get(IDENT_KEY_WAITING_TIME).asInt();
		}
		
		public void registerCompany(Company comp){
			this.comp = comp;
		}
		
		public Company getAssociatedCompany(){
			return comp;
		}
		
		public JsonNode toJson(JsonNodeFactory jnf){
			ObjectNode node = new ObjectNode(jnf);
			node.put(IDENT_KEY_ID, id);
			node.put(IDENT_KEY_COMPANY_ID, company_id);
			node.put(IDENT_KEY_NAME, name);
			node.put(IDENT_KEY_TIME, time);
			node.put(IDENT_KEY_WAITING_TIME, waiting_time);
			return (JsonNode) node;
		}
		
		public String getName(){
			return name;
		}
		
		public int getId(){
			return id;
		}
		
		public int getCompanyId(){
			return company_id;
		}
		
		public long getTime(){
			return time;
		}
		
		public int getWaitingTime(){
			return waiting_time;
		}
	
		public int getSLATimeDelta(){
			if(comp != null)
				return comp.getSLATime() - waiting_time;
			else
				return 0;	// should never be the case, but just to be sure
		}
	}
	
	public class Company {
		private String name;					// The name of the company
		private int id;							// The unique ID of the company
		
		private int sla_time;					// The SLA (Service Level Agreement) time of this company in seconds
		private float sla_percentage_limit;		// The SLA (Service Level Agreement) percentage of this company as float
		private float sla_percentage_current;	// The current SLA percentage of this company in this month (e.g. 0.95 would mean that IDnow achieved an SLA_percentage of 95% for this company for this month. If this is lower than SLA_percentage at the end of the month, we would not have reached to agreed SLA)
		
		private ArrayList<Identification> assidents;  // References to associated identification objects => saves time when sorting the list
		
		
		public Company(JsonNode comp){
			// e.g. {\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}
			
			name 		 		   = comp.get(COMP_KEY_NAME).asText();
			id 			 		   = comp.get(COMP_KEY_ID).asInt();
			sla_time 	 		   = comp.get(COMP_KEY_SLA_TIME).asInt();
			sla_percentage_limit   = (float) comp.get(COMP_KEY_SLA_PERC_LIMIT).asDouble();
			sla_percentage_current = (float) comp.get(COMP_KEY_SLA_PERC_CURRENT).asDouble();
			
			assidents = new ArrayList<Identification>();
		}
		
		public void registerIdentification(Identification ident){
			assidents.add(ident);
		}
		
		public ArrayList<Identification> getAssociatedIdentifications(){
			return assidents;
		}
		
		public JsonNode toJson(JsonNodeFactory jnf){
			ObjectNode node = new ObjectNode(jnf);
			node.put(COMP_KEY_ID, id);
			node.put(COMP_KEY_NAME, name);
			node.put(COMP_KEY_SLA_TIME, sla_time);
			node.put(COMP_KEY_SLA_PERC_LIMIT, (double) sla_percentage_limit);
			node.put(COMP_KEY_SLA_PERC_CURRENT, (double) sla_percentage_current);
			return (JsonNode) node;
		}
		
		public String getName(){
			return name;
		}
		
		public int getId(){
			return id;
		}
		
		public int getSLATime(){
			return sla_time;
		}
		
		public float getSLAPercentageLimit(){
			return sla_percentage_limit;
		}
		
		public float getSLAPercentageCurrent(){
			return sla_percentage_current;
		}
		
		public float getSLAPercentageDelta(){
			return sla_percentage_current - sla_percentage_limit;
		}
	}
}
