package service.company;

import com.google.inject.ImplementedBy;
import com.google.inject.Singleton;
import model.Company;

import java.util.HashMap;
import java.util.Map;

@ImplementedBy(CompanyServiceImpl.class)
public interface CompanyService {
    void addCompany(Company c);
    Company getCompany(int id);
    Map<Integer, Company> getCompanyMap();
}

@Singleton
class CompanyServiceImpl implements CompanyService {
    Map<Integer, Company> companyMap = new HashMap<>();

    @Override
    public void addCompany(Company c) {
        companyMap.put(c.getId(), c);
    }

    @Override
    public Company getCompany(int id) {
        return companyMap.get(id);
    }

    @Override
    public Map<Integer, Company> getCompanyMap() {
        return companyMap;
    }
}