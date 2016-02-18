package services;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import models.Company;
import play.Logger;

import com.google.inject.Singleton;

/**
 * Created by bduisenov on 18/02/16.
 */
@Singleton
public class CompanyService {

    private final static Logger.ALogger logger = Logger.of(CompanyService.class);

    /**
     * not sure how often they would be added. but assume that 'read' operations
     * would be more than writes
     */
    private final CopyOnWriteArraySet<Company> companies = new CopyOnWriteArraySet<>();

    public void addCompany(Company company) {
        logger.debug("addCompany({})", company);
        companies.add(company);
    }

    /**
     * stub for list of companies
     * @return
     */
    public Set<Company> getAllCompanies() {
        Set<Company> result = Collections.unmodifiableSet(this.companies);
        logger.debug("getAllCompanies(): returned {}", result);
        return result;

    }

}
