package service;

import api.Company;
import api.Identification;
import api.WeightSettings;
import models.CompanyEntity;
import models.IdentificationEntity;
import models.IdentificationStatus;
import models.WeightSettingsEntity;
import repository.CompanyRepository;

import javax.inject.Inject;

/**
 * Converts api models to dbo and vice versa
 *
 * @author Sergii R.
 * @since 17/02/19
 */
//TODO:: in future can be splitted on multiple converters
public class ConversionService {

    private final CompanyRepository companyRepository;

    @Inject
    public ConversionService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanyEntity convert(Company company) {
        return CompanyEntity.builder()
                .id(company.getId())
                .name(company.getName())
                .slaTime(company.getSlaTime())
                .slaPercentage(company.getSlaPercentage())
                .currentSlaPercentage(company.getCurrentSlaPercentage())
                .build();
    }

    public Company convert(CompanyEntity company) {
        return Company.builder()
                .id(company.getId())
                .name(company.getName())
                .slaTime(company.getSlaTime())
                .slaPercentage(company.getSlaPercentage())
                .currentSlaPercentage(company.getCurrentSlaPercentage())
                .build();
    }

    public IdentificationEntity convert(Identification identification) {
        IdentificationEntity identificationEntity = IdentificationEntity.builder()
                .id(identification.getId())
                .name(identification.getName())
                .time(identification.getTime())
                .waitingTime(identification.getWaitingTime())
                .identificationStatus(IdentificationStatus.NEW)
                .build();
        CompanyEntity companyEntity = companyRepository.getById(identification.getCompanyId());
        identificationEntity.setCompanyEntity(companyEntity);
        return identificationEntity;
    }

    public Identification convert(IdentificationEntity identificationEntity) {
        return Identification.builder()
                .id(identificationEntity.getId())
                .name(identificationEntity.getName())
                .time(identificationEntity.getTime())
                .waitingTime(identificationEntity.getWaitingTime())
                .companyId(identificationEntity.getCompanyEntity().getId())
                .build();
    }

    public WeightSettingsEntity convert(WeightSettings weightSettings) {
        WeightSettingsEntity weightSettingsEntity = new WeightSettingsEntity();
        weightSettingsEntity.setId(weightSettings.getId());
        weightSettingsEntity.setSlaDifferenceWeight(weightSettings.getSlaDifferenceWeight());
        weightSettingsEntity.setSlaWeight(weightSettings.getSlaWeight());
        weightSettingsEntity.setWaitingTimeWeight(weightSettings.getWaitingTimeWeight());
        return weightSettingsEntity;
    }
}
