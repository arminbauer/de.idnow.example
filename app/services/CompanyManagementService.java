package services;

import com.google.inject.ImplementedBy;
import services.dto.CompanyDTO;
import services.exceptions.InvalidCompanyException;
import services.impl.CompanyManagementServiceImpl;

/**
 * Created by ebajrami on 4/23/16.
 */
@ImplementedBy(CompanyManagementServiceImpl.class)
public interface CompanyManagementService {
    /**
     * Saves company passed as companyDTO
     *
     * @param companyDTO company to be persisted
     * @throws InvalidCompanyException if company with specified by id already exists
     */
    void addCompany(CompanyDTO companyDTO) throws InvalidCompanyException;

}
