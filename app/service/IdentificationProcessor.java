package service;

import com.google.inject.ImplementedBy;
import exception.NoCompanyAssociatedException;
import model.Company;
import model.Identification;
import service.impl.DefaultIdentificationProcessor;

import java.util.List;

@ImplementedBy(value = DefaultIdentificationProcessor.class)
public interface IdentificationProcessor {

    void addNewIdentification(Identification identification) throws NoCompanyAssociatedException;

    void updateCompanyInformation(Company company);

    List<Identification> getOrderedPendingIdentifications();
}
