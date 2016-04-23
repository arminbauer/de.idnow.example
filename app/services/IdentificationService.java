package services;

import com.google.inject.ImplementedBy;
import services.dto.IdentificationDTO;
import services.exceptions.InvalidCompanyException;
import services.impl.IdentificationServiceImpl;

import java.util.List;

/**
 * Created by ebajrami on 4/23/16.
 */
@ImplementedBy(IdentificationServiceImpl.class)
public interface IdentificationService {
    void startIdentification(IdentificationDTO identificationDTO) throws InvalidCompanyException;

    List<IdentificationDTO> getPendingIdentifications();
}
