package service;


import play.libs.Json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.Utility;
import models.Company;
import models.Identification;

public class IndetificationServiceImpl implements IndentificationServiceI {
	
	/* (non-Javadoc)
	 * @see service.IndentificationServiceI#getOptimalOrder()
	 */
	@Override
	public void getOptimalOrder(JsonNode json) {
	  // read all the values then create a hashmap with the order.
		
		List<Company> companyList =  new ArrayList<Company>();
		List<Identification> idList = new ArrayList<Identification>();
		
		for (int i = 0; i < json.size(); i++) {
			
			JsonNode node = json.get(i);
			//get the key values 
			if(i <= 1) { //the first two objects in JSON are defaulty considered as Company json
				System.out.println("This is company");
			Company c1 =  Json.fromJson(node, Company.class);
			System.out.println(c1.getCompanyId());
			System.out.println(c1.getCompanyName());
			companyList.add(c1);
			c1 = null;
			} else { //the rest are identifications for the first two companies
				Identification identification = Json.fromJson(node, Identification.class);
				System.out.println("This is identification");
				System.out.println("The identitfication::" + identification.getCustomerName());
				idList.add(identification);
				identification = null;
			}
		}
	}
	
	private void findOrder(List<Company> companyList, List<Identification> idList) {
		
		//find the company 
		
		
	}

    	
	
	

}
