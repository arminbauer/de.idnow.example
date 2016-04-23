package services;

import com.google.inject.ImplementedBy;
import repositories.models.Company;
import services.dto.CompanyDTO;
import services.exceptions.InvalidCompanyException;
import services.impl.CompanyManagementServiceImpl;

import java.util.List;

/**
 * Created by ebajrami on 4/23/16.
 */
@ImplementedBy(CompanyManagementServiceImpl.class)
public interface CompanyManagementService {
    void addCompany(CompanyDTO companyDTO) throws InvalidCompanyException;

    /**
     * TODO remove it later
     *
     * @return
     */
    List<Company> findAll();
}
