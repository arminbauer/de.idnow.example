package services;

import com.google.inject.ImplementedBy;
import services.dto.IdentificationDTO;
import services.exceptions.DuplicateIdentificationException;
import services.exceptions.InvalidCompanyException;
import services.impl.IdentificationServiceImpl;

import java.util.List;

/**
 * Service wraps implementation of identification methods
 * <p>
 * Created by ebajrami on 4/23/16.
 */
@ImplementedBy(IdentificationServiceImpl.class)
public interface IdentificationService {
    /**
     * @param identificationDTO Identification object which is then added to the current list of open identifications
     * @throws InvalidCompanyException          Company set in request cannot be found
     * @throws DuplicateIdentificationException Identification with specified ID already exists
     */
    void startIdentification(IdentificationDTO identificationDTO) throws InvalidCompanyException, DuplicateIdentificationException;

    /**
     * Returns list of identifications. The identifications should be ordered in the optimal order regarding the SLA of the company,
     * the waiting time of the ident and the current SLA percentage of that company. More urgent idents come first.
     *
     * @return
     */
    List<IdentificationDTO> getPendingIdentifications();
}
