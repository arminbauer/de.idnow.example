package repository;

import com.google.inject.ImplementedBy;
import models.Company;

import java.util.List;

@ImplementedBy(CompanyRepositoryImpl.class)
public interface CompanyRepository {

    void save(Company company);

    List<Company> getAll();
}
