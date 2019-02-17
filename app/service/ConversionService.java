package service;

import api.Company;
import api.Identification;
import models.CompanyEntity;
import models.IdentificationEntity;
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
    public ConversionService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public CompanyEntity convert(Company company){
        return CompanyEntity.builder()
                .id(company.getId())
                .name(company.getName())
                .slaTime(company.getSlaTime())
                .slaPercentage(company.getSlaPercentage())
                .currentSlaPercentage(company.getCurrentSlaPercentage())
                .build();
    }

    public Company convert(CompanyEntity company){
        return Company.builder()
                .id(company.getId())
                .name(company.getName())
                .slaTime(company.getSlaTime())
                .slaPercentage(company.getSlaPercentage())
                .currentSlaPercentage(company.getCurrentSlaPercentage())
                .build();
    }

    public IdentificationEntity convert(Identification identification){
        IdentificationEntity identificationEntity = IdentificationEntity.builder()
                .id(identification.getId())
                .name(identification.getName())
                .time(identification.getTime())
                .waitingTime(identification.getWaitingTime())
                .build();
        CompanyEntity companyEntity = companyRepository.getById(identification.getId());
        identificationEntity.setCompanyEntity(companyEntity);
        return identificationEntity;
    }
}
