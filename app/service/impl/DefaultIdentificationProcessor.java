package service.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import exception.NoCompanyAssociatedException;
import model.Company;
import model.Identification;
import service.IdentificationProcessor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Singleton
public class DefaultIdentificationProcessor implements IdentificationProcessor{

    private Map<Integer, InternalCompany> companyMap;

    /*
    * In this task I don't have enough information to define the exact order I would put the identifications in.
    * I would also like to know the current number of processed identifications of the company to understand how will
    * successfully or unsuccessfully processed identification affect the currentSlaPercentage.
    * I would also like to know the estimation of processing time of one identification to understand
    * where we would need to shuffle identifications (even breaking SLA order) to make the most of them processed.
    * */
    private Set<InternalIdentification> identificationSet;

    @Inject
    private DefaultIdentificationProcessor(){
        companyMap = new ConcurrentHashMap<>();
        identificationSet = new ConcurrentSkipListSet<>(getPendingIdentificationComparator());
    }

    @Override
    public void addNewIdentification(Identification identification) throws NoCompanyAssociatedException {
        InternalCompany company = companyMap.get(identification.getCompanyId());
        if (company == null) {
            throw new NoCompanyAssociatedException(identification.getCompanyId());
        }
        InternalIdentification internalIdentification = new InternalIdentification(identification, company);
        identificationSet.add(internalIdentification);
    }

    @Override
    public void updateCompanyInformation(Company company) {
        if (company.getSlaPercentage() == null) {
            throw new IllegalArgumentException("Can't add company without an sla percentage");
        }
        companyMap.put(company.getId(), new InternalCompany(company));
    }

    @Override
    public List<Identification> getOrderedPendingIdentifications() {
        return new ArrayList<>(identificationSet);
    }

    /*
    * This comparator is based on my understanding of the task and the information I have.
    * 1. All already expired (waiting time > SLA) are put to the end of the set.
    * 2. All under-performing companies (current SLA % <= SLA %) are put before others
    * 3. All critical identifications with very small remaining time left are prioritized.
    * 4. Company inside under-performing and over-performing category are ranged by current SLA % / SLA % ratio
    * 5. If all other parameters are equal, the identification are ordered ascending by time to fail.
    * */
    private Comparator<InternalIdentification> getPendingIdentificationComparator() {
        return Comparator.comparing(InternalIdentification::isAlreadyFailed)
                .thenComparing(InternalIdentification::isOverPerformingCompany)
                .thenComparing(InternalIdentification::isInCriticalZone, Comparator.reverseOrder())
                .thenComparing(InternalIdentification::getCompanyPerformanceRatio)
                .thenComparing(InternalIdentification::getTimeToFail);
    }
}
