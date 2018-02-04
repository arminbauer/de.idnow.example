package services;

import com.google.inject.ImplementedBy;
import models.CompanyDto;
import services.impl.CompanyServiceImpl;

/**
 * @author prasa on 03-02-2018
 * @project de.idnow.example
 */
@ImplementedBy(CompanyServiceImpl.class)
public interface CompanyService {

    CompanyDto add(CompanyDto company);

    CompanyDto getById(Long id);

}
