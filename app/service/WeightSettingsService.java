package service;

import api.WeightSettings;
import models.WeightSettingsEntity;
import repository.WeightSettingsRepository;

import javax.inject.Inject;

/**
 * Weighting settings service
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class WeightSettingsService {
    private WeightSettingsRepository weightSettingsRepository;
    private ConversionService conversionService;

    @Inject
    public WeightSettingsService(WeightSettingsRepository weightSettingsRepository, ConversionService conversionService) {
        this.weightSettingsRepository = weightSettingsRepository;
        this.conversionService = conversionService;
    }

    public boolean addWeightSettings(WeightSettings weightSettings) {
        return weightSettingsRepository.save(conversionService.convert(weightSettings));
    }

    public WeightSettingsEntity getWeightSettings() {
        return weightSettingsRepository.getWeightSettings();
    }
}
