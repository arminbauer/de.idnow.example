package models;

import java.util.*;
import models.storage.*;

public class Storage {

  private List<Company> companies;                         // List of all registered companies
  private List<Identification> identifications;            // List of all registered identifications

  public Storage() {
    this.companies       = new ArrayList<Company>();
    this.identifications = new ArrayList<Identification>();
  }

  // returns added company from storage to allow chaining
  public Company addCompany(Company newCompany) throws StorageElementAlreadyExistsException, StorageIntegrityException {
    // some validations

    // check if we have the company already in our storage
    if (null != getCompanyById(newCompany.getId())) {
      throw new StorageElementAlreadyExistsException("Company with id " + newCompany.getId());
    }

    // additional checks could go for name or id-name key

    companies.add(newCompany);

    // return company from storage to ensure company was saved successful
    Company companyFromStorage = getCompanyById(newCompany.getId());
    if (null == companyFromStorage) {
      throw new StorageIntegrityException();
    }
    return(companyFromStorage);
  }

  public Company getCompanyById(int companyId) {
    for (Company company : companies) {
      if (company.getId() == companyId) {
        return company;
      }
    }
    return null;
  }

  public int getCompanyCount() {
    return companies.size();
  }

  // returns added identification from storage to allow chaining
  public Identification addIdentification(Identification newIdentification) throws StorageElementAlreadyExistsException, StorageIntegrityException, StorageCompanyNotFoundException {
    // some validations

    // check if we have the company already in our storage
    if (null != getIdentificationById(newIdentification.getId())) {
      throw new StorageElementAlreadyExistsException("Identification with id " + newIdentification.getId());
    }
    if (null == getCompanyById(newIdentification.getCompanyId())) {
      throw new StorageCompanyNotFoundException("" + newIdentification.getCompanyId());
    }

    identifications.add(newIdentification);

    // return company from storage to ensure company was saved successful
    Identification identificationFromStorage = getIdentificationById(newIdentification.getId());
    if (null == identificationFromStorage) {
      throw new StorageIntegrityException();
    }
    return(identificationFromStorage);
  }

  public Identification getIdentificationById(int identificationId) {
    for (Identification identification : identifications) {
      if (identification.getId() == identificationId) {
        return identification;
      }
    }
    return null;
  }

  public List<Identification> getIdentificationByCompanyId(int companyId) {
    List<Identification> identificationList = new ArrayList<Identification>();

    for (Identification identification : identifications) {
      if (identification.getCompanyId() == companyId) {
        identificationList.add(identification);
      }
    }
    return identificationList;
  }

  public int getIdentificationCount() {
    return identifications.size();
  }

  public List<Identification> getSortedIdentifications() {
    // 1. get companies sorted by their SLA_time asc, SLA_percentage asc
    // 2. get their identifications sorted by their waiting_time desc

    List<Identification> sortdIdentifications = new ArrayList<Identification>();
    for (Company company : getSortedCompanies()) {
      sortdIdentifications.addAll(getSortedIdentificationsForCompany(company.getId()));
    }
    return sortdIdentifications;
  }

  // return companies sorted by their SLA_time asc, SLA_percentage asc
  private List<Company> getSortedCompanies() {
    Collections.sort(companies, new Comparator<Company>() {
      @Override
      public int compare(Company c1, Company c2) {
        if (c1.getSLATime() > c2.getSLATime()) {
          return 1;                    // c1 has higher SLATime as c2 => c1 after c2
        } else if (c1.getSLATime() < c2.getSLATime()) {
          return -1;                   // c1 has lower SLATime as c2 => c1 before c2
        }
        // SLA_time is same, so check urgency (by SLA_percentage - current_SLA_percentage) - the higher, the more important
        float urgency1 = c1.getSLAPercentage() - c1.getCurrentSLAPercentage();
        float urgency2 = c2.getSLAPercentage() - c2.getCurrentSLAPercentage();
        if (urgency1 > urgency2) {
          return -1;                   // c1 has higher urgency as c2 => c1 before c2
        } else if (urgency1 < urgency2) {
          return 1;                    // c1 has lower urgency as c2 => c1 after c2
        }
        return 0;
      }
    });
    return companies;
  }

  private List<Identification> getSortedIdentificationsForCompany(int companyId) {
    List<Identification> identifications = getIdentificationByCompanyId(companyId);
    Collections.sort(identifications, new Comparator<Identification>() {
      @Override
      public int compare(Identification i1, Identification i2) {
        if (i1.getWaitingTime() > i2.getWaitingTime()) {
          return -1;                   // i1 has a higher waiting time as i2 => i1 before i2
        } else if (i1.getWaitingTime() < i2.getWaitingTime()) {
          return 1;                    // i1 has a lower waiting time as i2 => i1 after i2
        }
        return 0;
      }
    });
    return identifications;
  }

}
