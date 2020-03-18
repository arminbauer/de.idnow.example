package dao;

import java.util.List;

import domain.Company;

public interface CompanyRepository {

	Company save(Company company);

	List<Company> load();

	Company loadById(Long id);

	boolean existsById(Long id);

}
