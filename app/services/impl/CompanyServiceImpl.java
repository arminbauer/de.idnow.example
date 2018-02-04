package services.impl;

import models.CompanyDto;
import persistence.CompanyEntity;
import persistence.dao.CompanyDao;
import services.CompanyService;

import javax.inject.Inject;

import static services.mappers.CompanyMapper.toDto;
import static services.mappers.CompanyMapper.toEntity;


/**
 * @author prasa on 03-02-2018
 * @project de.idnow.example
 */
public class CompanyServiceImpl implements CompanyService {

    @Inject
    CompanyDao dao;

    public CompanyServiceImpl() {
        super();
    }

    @Override
    public CompanyDto add(CompanyDto company) {
        CompanyEntity entity = toEntity(company);
        return toDto(dao.add(entity));
    }

    @Override
    public CompanyDto getById(Long id) {
        return toDto(dao.getById(id));
    }

}
