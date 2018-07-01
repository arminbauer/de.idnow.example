package service;

import com.google.inject.ImplementedBy;
import models.Identification;

import java.util.List;

@ImplementedBy(IdentificationServiceImpl.class)
public interface IdentificationService {

    List<Identification> getIdentificationsOrderBySLA();

    void save(Identification identification);
}
