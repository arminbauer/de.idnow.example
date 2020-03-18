package service;

import java.util.Comparator;

import domain.Identification;

public class IdentificationComparator implements Comparator<Identification> {

	@Override
	public int compare(Identification o1, Identification o2) {
		int result;
		if (o1.company.id.equals(o2.company.id)) {
			result = Integer.compare(o1.waitingTime, o2.waitingTime);
		} else {
			// it is too late to increase o1 slaPercentage
			if (o1.company.slaTime < o1.waitingTime) {
				return -1;
				// it is too late to increase o2 slaPercentage
			} else if (o2.company.slaTime < o2.waitingTime) {
				return 1;
			}
			final float diffExpectedCurrentSla1 = o1.company.currentSlaPercentage - o1.company.slaPercentage;
			final float diffExpectedCurrentSla2 = o2.company.currentSlaPercentage - o2.company.slaPercentage;

			// the smaller difference between expectation and current percentage
			result = Math.negateExact(Float.compare(diffExpectedCurrentSla1, diffExpectedCurrentSla2));
			// check also for Math.abs(diffExpectedCurrentSla1 - diffExpectedCurrentSla2) <
			// 0.01 ?
			if (result == 0) {
				result = Math.negateExact(
						Integer.compare(o1.company.slaTime - o1.waitingTime, o2.company.slaTime - o2.waitingTime));
			}
		}

		return result;
	}

}