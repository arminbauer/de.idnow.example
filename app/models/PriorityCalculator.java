package models;

import play.Logger;

/**
 * This class is responsible for priority calculation
 * 
 * @author leonardo.cruz
 *
 */
public class PriorityCalculator {
	
	private Identification identification;
	private Company company;
	
	public PriorityCalculator(Identification identification, Company company) {
		super();
		this.identification = identification;
		this.company = company;
	}
	
	/**
	 * Calculates the priority for associated identification based on waiting time and company SLA.
	 * Lower values have more priority.id
	 *
	 * @return
	 */
	public long getPriority() {
		
		long companySLA = (long) ((company.getCurrentSlaPercentage() * 1000) / company.getSlaPercentage());
		
		long waitingTime =(company.getSlaTime() * 1000) / identification.getWaitingTime();
		
	    return Long.parseLong("" + companySLA + waitingTime);
		
	}
	
	

}
