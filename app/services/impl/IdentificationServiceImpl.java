package services.impl;

import static services.mapper.IdentificationMapper.toDto;
import static services.mapper.IdentificationMapper.toEntity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import models.IdentificationDTO;
import persistence.CompanyEntity;
import persistence.IdentificationEntity;
import persistence.dao.CompanyDAO;
import persistence.dao.IdentificationDAO;
import services.IdentificationService;

public class IdentificationServiceImpl implements IdentificationService {

	@Inject
	CompanyDAO companyDao;

	@Inject
	IdentificationDAO identificationDAO;

	@Override
	public void add(IdentificationDTO identificationDto) {
		CompanyEntity company = companyDao.getById(identificationDto
				.getCompanyId());
		if (company == null)
			return;
		IdentificationEntity identification = toEntity(identificationDto);
		identification.setCompany(company);
		identificationDAO.add(identification);
	}

	@Override
	public List<IdentificationDTO> getAll() {
		List<IdentificationDTO> identificationDtos = null;
		List<IdentificationEntity> identificationEntities = identificationDAO
				.getAll();
		if (identificationEntities != null) {
			IdentificationComparator comparator=new IdentificationComparator();
			Collections.sort(identificationEntities,Collections.reverseOrder(comparator));
			identificationDtos = new LinkedList<IdentificationDTO>();
			for (IdentificationEntity identificationEntity : identificationEntities) {
				identificationDtos.add(toDto(identificationEntity));
			}
		}
		return identificationDtos;
	}

}
