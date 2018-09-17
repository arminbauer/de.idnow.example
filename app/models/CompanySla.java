package models;

import java.util.Comparator;

public class CompanySla implements Comparator<Company> {
	

	@Override
	public int compare(Company o1, Company o2) {
             if(o1.getSlaTime() < o2.getSlaTime()) {
            	 
            	 return -1;
             } else if(o1.getSlaTime() < o2.getSlaTime()) {
            	 return -1;
             } else   return 0  ;
	}
}
