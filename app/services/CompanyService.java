package services;

import models.CompanyDTO;
import services.impl.CompanyServiceImpl;

import com.google.inject.ImplementedBy;
@ImplementedBy(CompanyServiceImpl.class)
public interface CompanyService {

	CompanyDTO add(CompanyDTO company);

	CompanyDTO getById(Long id);

}
