package sorters;

import java.util.List;

import models.Company;

public class SortBySlaFulfillment implements Sorting {

	private List<Company> companies;
	
	public SortBySlaFulfillment(List<Company> comps) {
		this.companies = comps;
	}

	@Override
	public PriorityList sort(PriorityList priorityList) {
		//TODO
		return priorityList;
	}

}
