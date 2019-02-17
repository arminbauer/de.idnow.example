package service.identification;

import api.Identification;
import models.IdentificationStatus;
import models.WeightSettingsEntity;
import repository.IdentificationRepository;
import service.ConversionService;
import service.WeightSettingsService;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Identification service
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class IdentificationsService {
    private IdentificationRepository identificationRepository;
    private IdentificationComparator identificationComparator;
    private ConversionService conversionService;
    private WeightSettingsService weightSettingsService;

    @Inject
    public IdentificationsService(IdentificationRepository identificationRepository,
                                  IdentificationComparator identificationComparator,
                                  ConversionService conversionService,
                                  WeightSettingsService weightSettingsService) {
        this.identificationRepository = identificationRepository;
        this.identificationComparator = identificationComparator;
        this.conversionService = conversionService;
        this.weightSettingsService = weightSettingsService;
    }


    /**
     * Add new identification if it doesn't exist in db
     *
     * @param identification to add
     * @return <code>true</code> if the identification was successfully added
     * <code>false</code> otherwise
     */
    public boolean addNewIdentification(Identification identification) {
        return identificationRepository.save(conversionService.convert(identification));
    }

    /**
     * Get the list of new identifications sorted regarding the SLAs of the companies
     *
     * @return the list of new identifications sorted regarding the SLAs of the companies
     */
    public List<Identification> getSortedIdentifications() {
        updateIdentificationComparator();
        return identificationRepository.getByStatus(IdentificationStatus.NEW).stream()
                .sorted(identificationComparator).map(identificationEntity -> conversionService.convert(identificationEntity))
                .collect(Collectors.toList());
    }

    /**
     * check if weighting settings are presented and update identification comparator
     */
    private void updateIdentificationComparator() {
        WeightSettingsEntity weightSettingsEntity = weightSettingsService.getWeightSettings();
        if (weightSettingsEntity != null) {
            if (weightSettingsEntity.getSlaDifferenceWeight() != null) {
                identificationComparator.setSlaDifferenceWeight(weightSettingsEntity.getSlaDifferenceWeight());
            }
            if (weightSettingsEntity.getSlaWeight() != null) {
                identificationComparator.setSlaWeight(weightSettingsEntity.getSlaWeight());
            }
            if (weightSettingsEntity.getWaitingTimeWeight() != null) {
                identificationComparator.setWaitingTimeWeight(weightSettingsEntity.getWaitingTimeWeight());
            }
        }
    }
}
