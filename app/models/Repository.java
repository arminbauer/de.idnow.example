package models;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

import play.Logger;

/**
 * This object emulates a persistent data store for companies and identifications.
 *
 * @author leonardo.cruz
 *
 */
@Singleton
public final class Repository {

    private Set<Company> companies = Collections.synchronizedSet(new HashSet<>());
    private Set<Identification> identifications = Collections.synchronizedSet(new HashSet<>());
    
  

    public Company create(Company newCompany) {
    	
    	if (null == newCompany.getId()) {
    		throw new RepositoryException("The company id must be provided.");
    	}
        if (companies.add(newCompany)) { 
        	return newCompany;
        } else {
        	throw new RepositoryException("There is already a company with this id " + newCompany.getId());	
        }
    }

    public Identification create(Identification newIdentification) {

    	if (null == newIdentification.getId()) {
    		throw new RepositoryException("The identification id must be provided.");
    	}

    	boolean associatedCompanyNotFound = companies
    			.stream()
    			.noneMatch(c -> c.getId().equals(newIdentification.getCompanyId()));

    	if (associatedCompanyNotFound) {
    		throw new RepositoryException("The associated company was not found: (" + newIdentification.getCompanyId() + ")");
    	}

        if (identifications.add(newIdentification)) { 
        	return newIdentification;
        } else {
        	throw new RepositoryException("There is already an identification with this id " + newIdentification.getId());	
        }
    }

    public Optional<Company> getCompany(Long id) {
        return companies
                .stream()
                .filter(company -> company.getId().equals(id))
                .findAny();
    }

    public Stream<Identification> getIdentification() {
        return identifications.stream();
    }

}
