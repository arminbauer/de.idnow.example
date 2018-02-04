package services.impl;

import static services.mappers.IdentificationMapper.toDto;
import static services.mappers.IdentificationMapper.toEntity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import models.IdentificationDto;
import persistence.CompanyEntity;
import persistence.IdentificationEntity;
import persistence.dao.CompanyDao;
import persistence.dao.IdentificationDao;
import services.IdentificationService;
import services.impl.utils.IdentificationComparator;

/**
 * @author prasa on 03-02-2018
 * @project de.idnow.example
 */
public class IdentificationServiceImpl implements IdentificationService {

    @Inject
    CompanyDao companyDao;

    @Inject
    IdentificationDao identificationDao;

    @Override
    public void add(IdentificationDto identificationDto) {
        CompanyEntity company = companyDao.getById(identificationDto
                .getCompanyId());
        if (company == null)
            return;
        IdentificationEntity identification = toEntity(identificationDto);
        identification.setCompany(company);
        identificationDao.add(identification);
    }

    @Override
    public List<IdentificationDto> getAll() {
        List<IdentificationDto> identificationDtos = null;
        List<IdentificationEntity> identificationEntities = identificationDao
                .getAll();
        if (identificationEntities != null) {
            IdentificationComparator comparator = new IdentificationComparator();
            Collections.sort(identificationEntities, Collections.reverseOrder(comparator));
            identificationDtos = new LinkedList<IdentificationDto>();
            for (IdentificationEntity identificationEntity : identificationEntities) {
                identificationDtos.add(toDto(identificationEntity));
            }
        }
        return identificationDtos;
    }
}
