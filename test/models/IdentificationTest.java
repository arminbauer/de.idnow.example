package models;

import model.Company;
import model.Identification;
import org.junit.After;
import org.junit.Test;
import repository.CompanyRepository;
import repository.IdentificationRepository;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class IdentificationTest {

  @After
  public void resetRepos() {
    CompanyRepository.resetCompanies();
    IdentificationRepository.resetPendingIdentifications();
  }

  @Test
  public void test_identifications_of_one_company_with_different_waiting_times() {
    // Given
    Map<Long, Company> companies = new HashMap();
    companies.put(100L, new Company(100, "ABC", 60, 0.9f, 0.95f));
    CompanyRepository.setCompanies(companies);

    Identification identification1 = new Identification(1, "id1", 123456, 30, 100L);
    Identification identification2 = new Identification(2, "id2", 456789, 45, 100L);

    // When
    IdentificationRepository.addIdentification(identification1);
    IdentificationRepository.addIdentification(identification2);

    // Then
    assertEquals(2, IdentificationRepository.getPendingIdentifications().peek().getId());
    assertEquals(45, IdentificationRepository.getPendingIdentifications().peek().getWaitingTime());
  }

  @Test
  public void company_with_lower_current_sla_percentage_has_higher_priority() {
    // Given
    Map<Long, Company> companies = new HashMap();
    companies.put(100L, new Company(100, "ABC", 60, 0.9f, 0.95f));
    companies.put(123L, new Company(123, "DEF", 60, 0.9f, 0.90f));
    CompanyRepository.setCompanies(companies);

    Identification identification1 = new Identification(1, "id1", 123456, 30, 100L);
    Identification identification2 = new Identification(2, "id2", 456789, 30, 123L);

    // When
    IdentificationRepository.addIdentification(identification1);
    IdentificationRepository.addIdentification(identification2);

    // Then
    assertEquals(2, IdentificationRepository.getPendingIdentifications().peek().getId());
    assertEquals(123L, IdentificationRepository.getPendingIdentifications().peek().getCompanyId());
  }

  @Test
  public void company_with_more_urgent_sla_time_has_higher_priority() {
    // Given
    Map<Long, Company> companies = new HashMap();
    companies.put(100L, new Company(100, "ABC", 60, 0.9f, 0.95f));
    companies.put(123L, new Company(123, "DEF", 120, 0.8f, 0.95f));
    CompanyRepository.setCompanies(companies);

    Identification identification1 = new Identification(1, "id1", 123456, 30, 100L);
    Identification identification2 = new Identification(2, "id2", 456789, 30, 123L);

    // When
    IdentificationRepository.addIdentification(identification1);
    IdentificationRepository.addIdentification(identification2);

    // Then
    assertEquals(1, IdentificationRepository.getPendingIdentifications().peek().getId());
    assertEquals(100L, IdentificationRepository.getPendingIdentifications().peek().getCompanyId());
  }
}
