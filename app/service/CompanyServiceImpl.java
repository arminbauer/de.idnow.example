package service;

import models.Company;
import repository.CompanyRepository;

import javax.inject.Inject;
import java.util.List;

public class CompanyServiceImpl implements CompanyService {

    @Inject
    private CompanyRepository companyRepository;

    @Override
    public void save(Company company) {
        // todo Do some checks here
        companyRepository.save(company);
    }

    @Override
    public List<Company> getAll() {
        return companyRepository.getAll();
    }
}
