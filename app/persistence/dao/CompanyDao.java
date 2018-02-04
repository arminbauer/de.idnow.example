package persistence.dao;

import persistence.CompanyEntity;
import play.db.jpa.JPA;

/**
 * @author prasa on 03-02-2018
 * @project de.idnow.example
 */
public class CompanyDao {

    public CompanyDao() {
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
