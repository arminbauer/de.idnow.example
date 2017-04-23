package database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dto.Company;
import service.IdentPojo;


/**
 * In-Memory database class
 * 
 * @author chanchal.nerkar
 *
 */
public class DatabaseClass {

	/**
	 * Below variables act as in-memory database.
	 */
	private static Map<Long, Company> companies =  new ConcurrentHashMap<Long, Company>();
	private static List<IdentPojo> identifications = new ArrayList<IdentPojo>();

	public static Map<Long, Company> getCompanies(){
		return companies;
	}

	public static List<IdentPojo> getIdentifications(){
		return identifications;
	}
}
