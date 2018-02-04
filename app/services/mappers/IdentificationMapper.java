package services.mappers;

import models.IdentificationDto;
import persistence.IdentificationEntity;

/**
 * @author prasa on 03-02-2018
 * @project de.idnow.example
 */
public class IdentificationMapper {
    public static IdentificationDto toDto(IdentificationEntity entity) {
        IdentificationDto dto = new IdentificationDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setTime(entity.getTime());
        dto.setWaitingTime(entity.getWaitingTime());
        dto.setCompanyId(entity.getCompany().getId());
        return dto;
    }

    public static IdentificationEntity toEntity(IdentificationDto dto) {
        IdentificationEntity entity = new IdentificationEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setTime(dto.getTime());
        entity.setWaitingTime(dto.getWaitingTime());
        return entity;
    }
}
