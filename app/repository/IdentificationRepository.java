package repository;

import com.google.inject.ImplementedBy;
import models.Identification;

import java.util.List;

@ImplementedBy(IdentificationRepositoryImpl.class)
public interface IdentificationRepository {

    void save(Identification identification);

    List<Identification> getIdentificationsOrderBySLA();
}
