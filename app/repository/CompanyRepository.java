package repository;

import errorhandling.NotFoundException;
import model.Company;
import play.db.jpa.JPA;

public class CompanyRepository {

	public boolean exists(Long id) {
		return JPA.em().find(Company.class, id) != null;
	}

	public Company findById(Long id) {
		Company company = JPA.em().find(Company.class, id);
		if (company == null) {
			throw new NotFoundException("Company with id " + id + " not found");
		}
		return company;
	}
}
