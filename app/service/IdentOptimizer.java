package service;

import java.util.Comparator;

/**
 *  IdentOptimizer contains sorting logic for identification jobs. The logic is as below - <br/>
 * <li> 1. Sort by delta SLA percentage . (SLA urgency) </li>
 * <li> 2. Sort by remaining time
 * @author chanchal.nerkar
 *
 */
public class IdentOptimizer implements Comparator<IdentPojo> {

	@Override
	public int compare(IdentPojo identLeft, IdentPojo identRight) {

		if(identLeft.getSlaPercentageDelta().equals(identRight.getSlaPercentageDelta())){
			return identLeft.getRemainingServiceTime().compareTo(identRight.getRemainingServiceTime());
		}
		return identLeft.getSlaPercentageDelta().compareTo(identRight.getSlaPercentageDelta());
	}

}
