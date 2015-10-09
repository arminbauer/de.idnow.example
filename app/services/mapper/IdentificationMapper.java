package services.mapper;

import models.IdentificationDTO;
import persistence.IdentificationEntity;

public class IdentificationMapper {
	public static IdentificationDTO toDto(IdentificationEntity entity) {
		IdentificationDTO dto = new IdentificationDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setTime(entity.getTime());
		dto.setWaitingTime(entity.getWaitingTime());
		dto.setCompanyId(entity.getCompany().getId());
		return dto;
	}
	public static IdentificationEntity toEntity(IdentificationDTO dto) {
		IdentificationEntity entity = new IdentificationEntity();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setTime(dto.getTime());
		entity.setWaitingTime(dto.getWaitingTime());
		return entity;
	}
}
