package service;

import java.util.List;

import dto.CompanyDto;

public interface CompanyService {

	void save(CompanyDto company);

	List<CompanyDto> load();

	CompanyDto loadById(Long uuid);

	boolean existsById(Long uuid);

}
