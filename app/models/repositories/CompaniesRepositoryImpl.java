package models.repositories;


import com.avaje.ebean.annotation.Transactional;
import models.Company;
import models.dtos.CompanyDto;

public class CompaniesRepositoryImpl implements CompaniesRepository {
	@Override
	public CompanyDto find(long id) {
		Company entity = Company.find.byId(id);
		return new CompanyDto(
				entity.id,
				entity.name,
				entity.slaTime,
				entity.slaPercentage,
				entity.currentSlaPercentage);
	}

	@Override
	@Transactional
	public void add(CompanyDto dto) {
		Company entity = new Company();
		entity.id = dto.getId();
		entity.name = dto.getName();
		entity.slaTime = dto.getSlaTime();
		entity.slaPercentage = dto.getSlaPercentage();
		entity.currentSlaPercentage = dto.getCurrentSlaPercentage();

		entity.save();
	}
}
