package util;

import models.Identification;

/**
 * Utility class for calculations.
 *
 * @author Markus Panholzer <markus.panholzer@eforce21.com>
 * @since 31.09.2017
 */
public class Calculator {

	/**
	 * Calculates the priority of a given identification.
	 * 
	 * @param request
	 *            the identification.
	 * @return the calculated priority.
	 */
	public static double calculatePriority(Identification request) {

		double byTime = request.getCompany().getSlaTime() / request.getWaitingTime();
		double byGoal = request.getCompany().getCurrentSlaPercentage() / request.getCompany().getSlaPercentage();
		// lower will be prioritized
		return byTime + byGoal;
	}

}
