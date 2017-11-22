package models.identification;

import com.google.inject.ImplementedBy;
import models.identification.entities.Identification;
import models.identification.jsons.IdentificationJson;
import models.store.IdNowStoreRepository;

import java.util.List;

@ImplementedBy(StoreIdentificationRepository.class)
public interface IdentificationRepository {
    IdentificationJson addIdentification(IdentificationJson json) throws IdNowStoreRepository.CompanyNotExist, IdNowStoreRepository.DuplicatedElementException;
    List<IdentificationJson> getSortedIdentificationList();
}
