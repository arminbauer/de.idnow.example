package models.identification;

import models.identification.comparator.SLAComparator;
import models.identification.entities.Identification;
import models.identification.factories.IdentificationFactory;
import models.identification.factories.IdentificationJsonFactory;
import models.identification.jsons.IdentificationJson;
import models.store.IdNowStoreRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StoreIdentificationRepository implements IdentificationRepository {
    private final IdNowStoreRepository idNowStoreRepository;
    private final SLAComparator slaComparator;

    @Inject
    public StoreIdentificationRepository(IdNowStoreRepository idNowStoreRepository, SLAComparator slaComparator) {
        this.idNowStoreRepository = idNowStoreRepository;
        this.slaComparator = slaComparator;
    }


    @Override
    public IdentificationJson addIdentification(IdentificationJson json)
            throws IdNowStoreRepository.CompanyNotExist, IdNowStoreRepository.DuplicatedElementException {
        idNowStoreRepository.addIdentification(
                IdentificationFactory.getInstance().fromIdentificationJson(json)
        );
        return json;
    }

    @Override
    public List<IdentificationJson> getSortedIdentificationList() {
        List<Identification> list = idNowStoreRepository.getIdentificationList()
                .stream().sorted(slaComparator).collect(Collectors.toList());

        List<IdentificationJson> rs = new ArrayList<>();

        list.stream().map(this::mapIdToJson).forEach(rs::add);

        return rs;
    }

    private IdentificationJson mapIdToJson(Identification identification) {
        return IdentificationJsonFactory.getInstance().fromIdentification(identification);
    }
}
