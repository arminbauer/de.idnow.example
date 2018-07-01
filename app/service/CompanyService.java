package service;

import com.google.inject.ImplementedBy;
import models.Company;

import java.util.List;

@ImplementedBy(CompanyServiceImpl.class)
public interface CompanyService {

    void save(Company company);

    List<Company> getAll();
}
