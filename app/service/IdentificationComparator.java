package service;

import java.util.Comparator;

import database.Repository;
import model.Company;
import model.Identification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdentificationComparator implements Comparator<Identification>{


	private static Logger logger = LoggerFactory.getLogger(IdentificationComparator.class);


	@Override
	public int compare(Identification id1, Identification id2) {

		Repository  repo = new Repository();
		Company company1 = repo.getCompanybyID(id1.getCompanyId());
		Company company2 = repo.getCompanybyID(id2.getCompanyId());
		long company1SLA = (long) (company1.getCurrentSlaPercentage() / company1.getSlaPercentage());
		long company2SLA = (long) (company2.getCurrentSlaPercentage() / company2.getSlaPercentage());
		
		/** If both companies are in same category (OverPerforming or UnderPerforming or equal),
		**  Prioritize based on time to fail
		***/
		if(((company1SLA > 1) && (company2SLA > 1)) ||
			((company1SLA < 1) && (company2SLA < 1)) ||
            ((company1SLA == 1) && (company2SLA == 1))) {
			Integer id1TimeToFail =  company1.getSlaTime() - id1.getWaitingTime();
			Integer id2TimeToFail =  company2.getSlaTime() - id2.getWaitingTime();
			/**
			 if id1 wait time is more than SLA time,then prioritize id2 and vice versa.
			 **/
			if(id1TimeToFail < 0){
				return 1;
			}else if(id2TimeToFail < 0){
				return -1;
			}
			return Integer.compare(id1TimeToFail, id2TimeToFail) > 0 ? 1 : -1;

		}
		else {
			/** one company is OverPerforming, other is UnderPerforming
			Prioritize based on SLA
			**/
			return Long.compare(company1SLA, company2SLA) > 0 ? 1 : -1;

		}
		
	}

}
