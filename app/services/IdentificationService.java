package services;

import services.dto.IdentificationDTO;

import java.util.List;

/**
 * Created by ebajrami on 4/23/16.
 */
public interface IdentificationService {
    void startIdentification(IdentificationDTO identificationDTO);

    List<IdentificationDTO> getPendingIdentifications();
}
