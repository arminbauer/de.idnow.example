package models;

import java.util.ArrayList;
import java.util.List;

public class KnownCompanies {

	private List<Company> companies;
	
	public KnownCompanies() {
		companies = new ArrayList<Company>();
	}
	
	public boolean addCompany(Company comp) {
		return companies.add(comp);
	}

	public List<Company> withIds(List<Integer> ids) {
		List<Company> filteredList = new ArrayList<Company>();
		for (Integer i: ids) {
			for (Company c: companies) {
				if (c.getid() == i) {
					filteredList.add(c);
				}
			}
		}
		
		return filteredList;
	}

}
