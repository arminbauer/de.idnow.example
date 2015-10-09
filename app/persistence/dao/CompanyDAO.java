package persistence.dao;

import persistence.CompanyEntity;
import play.db.jpa.JPA;

public class CompanyDAO {
	
	public CompanyDAO() {
		super();
	}

	public CompanyEntity add(CompanyEntity company) {
		JPA.em().persist(company);
		return company;
	}

	public CompanyEntity getById(Long id) {
		return JPA.em().find(CompanyEntity.class, id);
	}
}
