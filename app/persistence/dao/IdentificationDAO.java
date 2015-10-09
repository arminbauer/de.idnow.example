package persistence.dao;

import java.util.List;

import javax.inject.Inject;

import persistence.IdentificationEntity;
import play.db.jpa.JPA;

public class IdentificationDAO {

	@Inject
	CompanyDAO companyDao;

	public IdentificationDAO() {
		super();
	}

	public IdentificationEntity add(IdentificationEntity identification) {
		JPA.em().persist(identification);
		return identification;
	}

	@SuppressWarnings("unchecked")
	public List<IdentificationEntity> getAll() {
		return (List<IdentificationEntity>) JPA.em()
				.createNamedQuery(IdentificationEntity.FINDALL_QUERY_NAME)
				.getResultList();
	}

}
