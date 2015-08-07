package util;

import models.Company;
import models.Identification;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maria.barbosz on 8/7/2015.
 */
public class IdentificationService {

    private static IdentificationService INSTANCE = new IdentificationService();

    private IdentificationService(){
    }

    public static IdentificationService getInstance(){
        return INSTANCE;
    }

    public List<String> computeOrder(){

        List<Company> companyList = DBEmulator.getInstance().getCompanyList();
        List<Identification> identificationList = new ArrayList<>(DBEmulator.getInstance().getIdentificationList());

        List<String> solutionList = new ArrayList<>();

        while (identificationList.size() > 0) {

            Company company = getCompanyWithSmallestSLADiff(companyList, identificationList);
            double smallestSlaDiff = company.getCurrentSlaPercentage() - company.getSlaPercentage();

            if (smallestSlaDiff > 0) {
                // no company has current SLA smaller then SLA (this is good :))
                // then get the identification that was the longer waiting time
                Identification identification = getIdentificationWithBiggestWaitingTime(identificationList);
                //if (identification != null) {
                    solutionList.add(identification.getName());
                    // remove this identification from the memory so it will not be computed again
                    //DBEmulator.getInstance().deleteIdentification(identification);
                    identificationList.remove(identification);
                //}
            } else {
                // there are companies that do not reach the SLA percentage target
                Identification identification = getIdentificationWithBiggestWaitingTime(identificationList, company.getId());

                //if (identification != null) {
                    solutionList.add(identification.getName());
                    // remove this identification from the memory so it will not be computed again
                    //DBEmulator.getInstance().deleteIdentification(identification);
                    identificationList.remove(identification);
                //}
            }
        };


        return solutionList;
    }


    private Company getCompanyWithSmallestSLADiff(List<Company> companyList, List<Identification> identificationList){

        // init with a max
        double slaDiff = 100.0;
        Company company = null;

        for(Company c : companyList){
            double currentSlaDiff = c.getCurrentSlaPercentage() - c.getSlaPercentage();

            //
            if (currentSlaDiff < slaDiff){

                // if company has identifications
                if(hasPendingIdentifications(identificationList, c.getId())){
                    slaDiff = currentSlaDiff;
                    company = c;
                }
            }
        }

        //
        return company;
    }

    public boolean hasPendingIdentifications(List<Identification> identificationList, String companyId){

        for(Identification identification : identificationList){
            if (identification.getCompanyId().equals(companyId)){
                return true;
            }
        }
        return false;
    }

    private Identification getIdentificationWithBiggestWaitingTime(List<Identification> identificationList){

        long biggestWaitingTime = 0;
        Identification identification = null;

        for(Identification i : identificationList){

            if (i.getWaitingTime() > biggestWaitingTime){
                biggestWaitingTime = i.getWaitingTime();
                identification = i;
            }
        }

        return identification;
    }

    private Identification getIdentificationWithBiggestWaitingTime(List<Identification> identificationList, String companyId){

        long biggestWaitingTime = 0;
        Identification identification = null;

        for(Identification i : identificationList){

            if (i.getCompanyId().equals(companyId)) {
                if (i.getWaitingTime() > biggestWaitingTime) {
                    biggestWaitingTime = i.getWaitingTime();
                    identification = i;
                }
            }
        }

        return identification;
    }


}
