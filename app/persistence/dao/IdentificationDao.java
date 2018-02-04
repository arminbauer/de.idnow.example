package persistence.dao;

import persistence.IdentificationEntity;
import play.db.jpa.JPA;

import javax.inject.Inject;
import java.util.List;

/**
 * @author prasa on 03-02-2018
 * @project de.idnow.example
 */
public class IdentificationDao {

    @Inject
    CompanyDao companyDao;

    public IdentificationDao() {
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
