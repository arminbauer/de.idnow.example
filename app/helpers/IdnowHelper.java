package helpers;

import models.Company;
import models.Identification;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The Idnow helper class.
 */
public class IdnowHelper {

    private static IdnowHelper instance = null;

    private List<Identification> identifications = new ArrayList<>();

    private List<Company> companies = new ArrayList<>();

    private IdnowHelper() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static IdnowHelper getInstance() {
        if (instance == null) {
            instance = new IdnowHelper();
        }
        return instance;
    }

    /**
     * Gets all identifications by company id.
     *
     * @param companyId       the company id
     * @return the all identifications by company id
     */
    private List<Identification> getAllIdentificationsByCompanyId(int companyId) {
        return identifications.stream().filter(i -> i.getCompanyId().equals(companyId))
                .collect(Collectors.toList());
    }

    /**
     * Gets identification by id.
     *
     * @param id the id
     * @return the identification by id
     */
    public Optional<Identification> getIdentificationById(Integer id) {
        return identifications.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    /**
     * Create identification identification.
     *
     * @param identification the identification
     * @return the identification
     */
    public Identification createIdentification(Identification identification) {
        Optional<Identification> ident = identifications.stream().parallel().filter(c -> identification.getId().equals(c.getId())).findFirst();
        if (!ident.isPresent()) {
            this.identifications.add(identification);
            return identification;
        } else {
            return null;
        }
    }

    /**
     * Gets all identifications.
     *
     * @return the all identifications
     */
    public List<Identification> getAllIdentifications() {
        return identifications;
    }

    /**
     * Gets pending identifications.
     *
     * @param companies the companies
     * @return the pending identifications
     */
    public List<List<Identification>> getPendingIdentifications(List<Company> companies) {
        List<List<Identification>> result = new ArrayList<>();
        for (Company company : companies) {
            List<Identification> identifications = getAllIdentificationsByCompanyId(company.getId());
            List<Identification> sorted = identifications.stream().sorted(Comparator.comparing(Identification::calculateWaitingTime)).collect(Collectors.toList());
            result.add(sorted);
        }

        return result;
    }

    /**
     * Create company.
     *
     * @param company the company
     * @return the company
     */
    public Optional<Company> createCompany(Company company) {
        Optional<Company> first = companies.stream().parallel().filter(c -> company.getId().equals(c.getId())).findFirst();
        if (!first.isPresent()) {
            this.companies.add(company);
            return first;
        } else {
            return Optional.empty();
        }
    }


    /**
     * Gets company by id.
     *
     * @param id the id
     * @return the company by id
     */
    public Optional<Company> getCompanyById(Integer id) {
        return companies.stream().filter(c -> c.getId().equals(id)).findFirst();
    }


    /**
     * Gets all companies.
     *
     * @return the all companies
     */
    public List<Company> getAllCompanies() {
        return companies;
    }


    /**
     * Returns Companies based on sort criteria
     *
     * @return the list of sorted Companies
     */
    public List<Company> sortCompanies() {
        return this.companies.stream().sorted(Comparator.comparing(Company::getCurrentSlaPercentage)
                .thenComparing(Company::urgencyValue))
                .collect(Collectors.toList());
    }

}
