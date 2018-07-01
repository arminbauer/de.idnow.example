package repository;

import com.avaje.ebean.Ebean;
import models.Company;

import java.util.List;

public class CompanyRepositoryImpl implements CompanyRepository {

    @Override
    public void save(Company company) {
        company.save();
    }

    @Override
    public List<Company> getAll() {
        return Ebean.find(Company.class).findList();
    }
}
