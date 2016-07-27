package controllers;
import java.util.*;

final class IdentificationComparator implements Comparator<Identification>{
	
	@Override
	public int compare(Identification first, Identification second){
		
		if(Double.compare(first.getCompany().getCurrentSlaPercentage(),second.getCompany().getCurrentSlaPercentage())< 0)
			return -1;
		if(Double.compare(first.getCompany().getCurrentSlaPercentage(),second.getCompany().getCurrentSlaPercentage()) > 0)
			return 1;
		
		if(Double.compare(first.getCompany().getSlaPercentage(),second.getCompany().getSlaPercentage())< 0)
			return 1;
		
		if(Double.compare(first.getCompany().getSlaPercentage(),second.getCompany().getSlaPercentage())> 0)
			return -1;
		
		if(first.getWaitingTime() < second.getWaitingTime())
			return 1;
		if(first.getWaitingTime() > second.getWaitingTime())
			return -1;
		
		return 0;
				
	}
	
}