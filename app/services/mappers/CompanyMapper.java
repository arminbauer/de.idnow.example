package services.mappers;

import models.CompanyDto;
import persistence.CompanyEntity;

/**
 * @author prasa on 03-02-2018
 * @project de.idnow.example
 */
public class CompanyMapper {

    public static CompanyEntity toEntity(CompanyDto dto) {
        CompanyEntity entity = new CompanyEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setSlaPercentage(dto.getSlaPercentage());
        entity.setCurrentSlaPercentage(dto.getCurrentSlaPercentage());
        entity.setSlaTime(dto.getSlaTime());
        return entity;
    }

    public static CompanyDto toDto(CompanyEntity entity) {
        CompanyDto dto = new CompanyDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSlaPercentage(entity.getSlaPercentage());
        dto.setCurrentSlaPercentage(entity.getCurrentSlaPercentage());
        dto.setSlaTime(entity.getSlaTime());
        return dto;
    }
}

