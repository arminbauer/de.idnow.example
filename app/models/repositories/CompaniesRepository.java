package models.repositories;

import com.google.inject.ImplementedBy;
import models.dtos.CompanyDto;

@ImplementedBy(CompaniesRepositoryImpl.class)
public interface CompaniesRepository {
	CompanyDto find(long id);
	void add(CompanyDto dto);
}
