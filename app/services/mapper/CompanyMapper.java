package services.mapper;

import models.CompanyDTO;
import persistence.CompanyEntity;

public class CompanyMapper {
	
	public static CompanyEntity toEntity(CompanyDTO dto) {
		CompanyEntity entity = new CompanyEntity();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setSlaPercentage(dto.getSlaPercentage());
		entity.setCurrentSlaPercentage(dto.getCurrentSlaPercentage());
		entity.setSlaTime(dto.getSlaTime());
		return entity;
	}
	
	public static CompanyDTO toDto(CompanyEntity entity) {
		CompanyDTO dto = new CompanyDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setSlaPercentage(entity.getSlaPercentage());
		dto.setCurrentSlaPercentage(entity.getCurrentSlaPercentage());
		dto.setSlaTime(entity.getSlaTime());
		return dto;
	}
}
