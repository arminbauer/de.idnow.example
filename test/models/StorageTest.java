import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.twirl.api.Content;
import play.Logger;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

import models.*;
import models.storage.*;

public class StorageTest {

  // Constructor tests

  @Test
  public void itCreatesEmptyStorage() {
    Storage s = new Storage();
    assertEquals(0, s.getCompanyCount());
    assertEquals(0, s.getIdentificationCount());
  }

  // Negative tests

  @Test
  public void it_raises_a_StorageElementAlreadyExistsException_when_adding_a_company_twice() {
    try {
      Storage s  = new Storage();
      Company c1 = new Company(1, "Company 1", 60, 0.9f); c1.setCurrentSLAPercentage(0.95f);
      addCompanyToStorageWithNoException(s, c1);
      s.addCompany(c1);
      fail("Expected StorageElementAlreadyExistsException by adding same company twice");
    } catch (StorageElementAlreadyExistsException e) {
      assertEquals("Element already exists in storage: Company with id 1", e.getMessage());
    } catch (StorageGenericException e) {
      fail("Unexpected Exception: " + e.getMessage());
    }
  }

  @Test
  public void it_raises_a_StorageCompanyNotFoundException_when_adding_a_identification_with_an_unknown_company() {
    try {
      Storage s  = new Storage();
      Company c1 = new Company(1, "Company 1", 60, 0.9f); c1.setCurrentSLAPercentage(0.95f);
      Identification i = getValidIdentification(1000, 2);
      addCompanyToStorageWithNoException(s, c1);
      s.addIdentification(i);
      fail("Expected StorageCompanyNotFoundException by adding a identification without its company registered before");
    } catch (StorageCompanyNotFoundException e) {
      assertEquals("No such company id in storage: 2", e.getMessage());
    } catch (StorageGenericException e) {
      fail("Unexpected Exception: " + e.getMessage());
    }
  }



  // positive tests

  @Test
  // create 10 companies with 10 identifications each
  public void it_can_store_multiple_companies_with_multiple_identifications_each() {
    Storage s = new Storage();
    for (int companyId = 0; companyId < 10; companyId++) {
      Company c = getValidCompany(companyId);
      addCompanyToStorageWithNoException(s, c);
      for (int y = 0; y < 10; y++) {
        int identificationId = companyId * 10 + y;
        Identification i = getValidIdentification(identificationId, companyId);
        addIdentificationToStorageWithNoException(s, i);
      }
    }
    assertEquals(10,  s.getCompanyCount());
    assertEquals(100, s.getIdentificationCount());
  }


  // example tests
  @Test
  public void it_sorts_correct_for_example_1(){
    try {
      Storage s  = new Storage();
      Company c1 = new Company(1, "Company 1", 60, 0.9f); c1.setCurrentSLAPercentage(0.95f);
      Identification i1 = new Identification(1000, "Static Identification Name", 1405197400, 30, 1);
      Identification i2 = new Identification(1001, "Static Identification Name", 1405197400, 60, 1);
      s.addCompany(c1);
      s.addIdentification(i1);
      s.addIdentification(i2);
      List<Identification> sortedIdentifications = s.getSortedIdentifications();

      // i2 -> i1
      assertEquals(i1, sortedIdentifications.get(1));
      assertEquals(i2, sortedIdentifications.get(0));
    } catch (StorageGenericException e) {
      fail("Unexpected StorageGenericException: " + e.getMessage());
    }
  }

  @Test
  public void it_sorts_correct_for_example_2(){
    try {
      Storage s  = new Storage();
      Company c1 = new Company(1, "Company 1", 60, 0.9f); c1.setCurrentSLAPercentage(0.95f);
      Company c2 = new Company(2, "Company 2", 60, 0.9f); c1.setCurrentSLAPercentage(0.90f);
      Identification i1 = new Identification(1000, "Static Identification Name", 1405197400, 60, 1);
      Identification i2 = new Identification(1001, "Static Identification Name", 1405197400, 60, 2);
      s.addCompany(c1);
      s.addCompany(c2);
      s.addIdentification(i1);
      s.addIdentification(i2);
      List<Identification> sortedIdentifications = s.getSortedIdentifications();

      // i2 -> i1
      assertEquals(i1.getId(), sortedIdentifications.get(1).getId());
      assertEquals(i2.getId(), sortedIdentifications.get(0).getId());
    } catch (StorageGenericException e) {
      fail("Unexpected StorageGenericException: " + e.getMessage());
    }
  }

  @Test
  public void it_sorts_correct_for_example_3(){
    try {
      Storage s  = new Storage();
      Company c1 = new Company(1, "Company 1",  60, 0.9f); c1.setCurrentSLAPercentage(0.95f);
      Company c2 = new Company(2, "Company 2", 120, 0.9f); c2.setCurrentSLAPercentage(0.95f);
      Identification i1 = new Identification(1000, "Static Identification Name", 1405197400, 30, 1);
      Identification i2 = new Identification(1001, "Static Identification Name", 1405197400, 30, 2);
      s.addCompany(c1);
      s.addCompany(c2);
      s.addIdentification(i1);
      s.addIdentification(i2);
      List<Identification> sortedIdentifications = s.getSortedIdentifications();

      // i1 -> i2
      assertEquals(i1.getId(), sortedIdentifications.get(0).getId());
      assertEquals(i2.getId(), sortedIdentifications.get(1).getId());
    } catch (StorageGenericException e) {
      fail("Unexpected StorageGenericException: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      fail("Unexpected IllegalArgumentException: " + e.getMessage());
    }
  }

  @Test
  public void it_sorts_correct_for_example_4(){
    try {
      Storage s  = new Storage();
      Company c1 = new Company(1, "Company 1",  60, 0.9f); c1.setCurrentSLAPercentage(0.95f);
      Company c2 = new Company(2, "Company 2", 120, 0.9f); c1.setCurrentSLAPercentage(0.80f);
      Identification i1 = new Identification(1000, "Static Identification Name", 1405197400, 45, 1);
      Identification i2 = new Identification(1001, "Static Identification Name", 1405197400, 30, 2);
      s.addCompany(c1);
      s.addCompany(c2);
      s.addIdentification(i1);
      s.addIdentification(i2);
      List<Identification> sortedIdentifications = s.getSortedIdentifications();

      // i1 -> i2
      assertEquals(i1.getId(), sortedIdentifications.get(0).getId());
      assertEquals(i2.getId(), sortedIdentifications.get(1).getId());
    } catch (StorageGenericException e) {
      fail("Unexpected StorageGenericException: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      fail("Unexpected IllegalArgumentException: " + e.getMessage());
    }
  }




  // factories
  private Company getValidCompany(int newId) {
    Company c = new Company(newId, "Static Company Name", 60, 0.9f);
    c.setCurrentSLAPercentage(0.95f);
    return c;
  }

  private Identification getValidIdentification(int newId, int companyId) {
    Identification i = new Identification(newId, "Static Identification Name", 1405197400, 430, companyId);
    return i;
  }





  // helpers
  private void addCompanyToStorageWithNoException(Storage s, Company c) {
    try {
      s.addCompany(c);
    } catch (StorageGenericException e) {
      fail("Unexpected StorageGenericException: " + e.getMessage());
    }
  }

  private void addIdentificationToStorageWithNoException(Storage s, Identification i) {
    try {
      s.addIdentification(i);
    } catch (StorageGenericException e) {
      fail("Unexpected StorageGenericException: " + e.getMessage());
    }
  }

}
