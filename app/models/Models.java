package models;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * Class used to store in memory the list of Companies and the list of Identifications
 *
 */
public class Models {
	public static HashMap<String, Company> companyList = new HashMap<String, Company>();
	public static ArrayList<Identification> identificationList = new ArrayList<Identification>();
}