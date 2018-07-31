package comparator;

import action.CompanyAction;
import java.util.Comparator;
import model.Company;
import model.Identification;

public class IdentificationComparator implements Comparator<Identification>
{
    @Override
    public int compare(Identification o1, Identification o2)
    {
        CompanyAction companyAction = new CompanyAction();
        // Same company scenario
        if(o1.getCompanyId().compareTo(o2.getCompanyId()) == 0){
            return Integer.compare(o1.getWaitingTime(), o2.getWaitingTime()) > 0 ? -1 : 1;
        }

        // Different companies scenario
        Company companyOfIdent1 = companyAction.getCompanybyId(o1.getCompanyId());
        Company companyOfIdent2 = companyAction.getCompanybyId(o2.getCompanyId());

        // Same values of SLA Time and SLA percentage
        if(Integer.compare(companyOfIdent1.getSlaTime(),companyOfIdent2.getSlaTime()) == 0
            && Double.compare(companyOfIdent1.getSlaPercentage(), companyOfIdent2.getSlaPercentage()) == 0){
            int ident1WaitingTimeDiff = companyOfIdent1.getSlaTime() - o1.getWaitingTime();
            int ident2WaitingTimeDiff = companyOfIdent2.getSlaTime() - o2.getWaitingTime();
            if(Integer.compare(ident1WaitingTimeDiff,ident2WaitingTimeDiff) == 0){
                return Double.compare(companyOfIdent1.getCurrentSlaPercentage(), companyOfIdent2.getCurrentSlaPercentage());
            }
            if(Double.compare(companyOfIdent1.getCurrentSlaPercentage(), companyOfIdent2.getCurrentSlaPercentage()) > 0){
                return Integer.compare(companyOfIdent1.getSlaTime() - o1.getWaitingTime(), ident2WaitingTimeDiff);
            } else {
                return Integer.compare(companyOfIdent2.getSlaTime() - o2.getWaitingTime(), ident1WaitingTimeDiff);
            }
        }

        if(Double.compare(companyOfIdent1.getCurrentSlaPercentage(), companyOfIdent1.getSlaPercentage()) >= 0
            && Double.compare(companyOfIdent2.getCurrentSlaPercentage(),companyOfIdent2.getSlaPercentage()) >= 0){
            int slaWaitingTimeDifference = companyOfIdent1.getSlaTime() - o1.getWaitingTime();
            return Integer.compare(companyOfIdent2.getSlaTime() - o2.getWaitingTime(), slaWaitingTimeDifference) > 0 ? -1 : 1;
        }

        return 0;
    }
}
