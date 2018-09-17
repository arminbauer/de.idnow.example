package service;


import play.libs.Json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import common.Utility;
import models.Company;
import models.CompanySla;
import models.Identification;

public class IndetificationServiceImpl implements IndentificationServiceI {
	
	/* (non-Javadoc)
	 * @see service.IndentificationServiceI#getOptimalOrder()
	 */
	@Override
	public List<Identification> getOptimalOrder(JsonNode json) {
	  // read all the values then create a hashmap with the order.
		
		List<Company> companyList =  new ArrayList<Company>();
		List<Identification> idList = new ArrayList<Identification>();
		List<Identification> sortedList = new ArrayList<Identification>();
		for (int i = 0; i < json.size(); i++) {
			
			JsonNode node = json.get(i);
			//get the key values 
			if(i <= 1) { //the first two objects in JSON are defaulty considered as Company json
			Company c1 =  Json.fromJson(node, Company.class);
			companyList.add(c1);
			c1 = null;
			} else { //the rest are identifications for the first two companies
				Identification identification = Json.fromJson(node, Identification.class);
				idList.add(identification);
				identification = null;
			}
		}
		Collections.sort(companyList, new CompanySla()); 
		//get the order of the company and set the same order for Identification list 
		for (int i = 0; i < companyList.size(); i++) {
			Company c = companyList.get(i);
			 Iterator<Identification> ideI = idList.iterator();
			 while(ideI.hasNext()) {
				 Identification  id = ideI.next();
				 if(c.getCompanyId().equals(id.getCompanyId())) {
					 sortedList.add(id);
				 }
			 }
		}
		return sortedList;
	}
}
