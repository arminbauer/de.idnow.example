package repository;

import model.Company;

import java.util.HashMap;
import java.util.Map;

public class CompanyRepository {

  private static Map<Long, Company> COMPANIES = new HashMap<>();

  public static void addCompany(Company company) {
    if (!COMPANIES.containsKey(company.getId())) {
      COMPANIES.put(company.getId(), company);
    }
  }

  public static void updateCompany(Company company) {
    if (COMPANIES.containsKey(company.getId())) {
      COMPANIES.put(company.getId(), company);
    }
  }

  public static Company getCompany(long id) {
    if (COMPANIES.containsKey(id)) {
      return COMPANIES.get(id);
    }

    return null;
  }

  public static void setCompanies(Map<Long, Company> COMPANIES) {
    CompanyRepository.COMPANIES = COMPANIES;
  }

  public static void resetCompanies() {
    COMPANIES.clear();
  }
}
