package services.impl;

import javax.inject.Inject;

import static services.mapper.CompanyMapper.*;
import models.CompanyDTO;
import persistence.CompanyEntity;
import persistence.dao.CompanyDAO;
import services.CompanyService;

public class CompanyServiceImpl implements CompanyService {

	@Inject
	CompanyDAO dao;

	public CompanyServiceImpl() {
		super();
	}

	@Override
	public CompanyDTO add(CompanyDTO company) {
		CompanyEntity entity = toEntity(company);
		return toDto(dao.add(entity));
	}

	@Override
	public CompanyDTO getById(Long id) {
		return toDto(dao.getById(id));
	}

}
