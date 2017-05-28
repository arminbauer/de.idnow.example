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

public class IdentificationTest {

  private static final int validId = 1000;
  private static final String validName = "Valid Name";
  private static final int validStartTime = 1405197400;
  private static final int validWaitingTime = 430;
  private static final int validCompanyId = 1;

  // Constructor tests
  
  // Negative tests
  @Test
  public void itRaisesAnIllegalArgumentExceptionOnInvalidId() {
    try {
      Identification i = new Identification(-1, validName, validStartTime, validWaitingTime, validCompanyId);
      fail("Expected IllegalArgumentException by invalid arguments");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "id may not be less than 0");
    }
  }

  @Test
  public void itRaisesAnIllegalArgumentExceptionOnInvalidNullName() {
    try {
      Identification i = new Identification(validId, null, validStartTime, validWaitingTime, validCompanyId);
      fail("Expected IllegalArgumentException by invalid arguments");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "name may not be null or empty");
    }
  }

  @Test
  public void itRaisesAnIllegalArgumentExceptionOnInvalidEmptylName() {
    try {
      Identification i = new Identification(validId, "", validStartTime, validWaitingTime, validCompanyId);
      fail("Expected IllegalArgumentException by invalid arguments");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "name may not be null or empty");
    }
  }

  @Test
  public void itRaisesAnIllegalArgumentExceptionOnInvalidNegativeStartTime() {
    try {
      Identification i = new Identification(validId, validName, -1, validWaitingTime, validCompanyId);
      fail("Expected IllegalArgumentException by invalid arguments");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "startTime may not be less than 0");
    }
  }

  @Test
  public void itRaisesAnIllegalArgumentExceptionOnInvalidFutureStartTime() {
    try {
      long futureUnixTime = System.currentTimeMillis() / 1000L + 100;
      Identification i = new Identification(validId, validName, (int)futureUnixTime, validWaitingTime, validCompanyId);
      fail("Expected IllegalArgumentException by invalid arguments");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "startTime may not be in the future");
    }
  }

  @Test
  public void itRaisesAnIllegalArgumentExceptionOnInvalidNegativeWaitingTime() {
    try {
      Identification i = new Identification(validId, validName, validStartTime, -5, validCompanyId);
      fail("Expected IllegalArgumentException by invalid arguments");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "waitingTime may not be less than 0");
    }
  }

  @Test
  public void itRaisesAnIllegalArgumentExceptionOnInvalidFutureEndTime() {
    try {
      long futureUnixTime = System.currentTimeMillis() / 1000L - 100;
      int  waitingTime    = 1000;
      Identification i = new Identification(validId, validName, (int)futureUnixTime, waitingTime, validCompanyId);
      fail("Expected IllegalArgumentException by invalid arguments");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "identification needs to have ended in the past");
    }
  }

  @Test
  public void itRaisesAnIllegalArgumentExceptionOnInvalidCompanyId() {
    try {
      Identification i = new Identification(validId, validName, validStartTime, validWaitingTime, -1);
      fail("Expected IllegalArgumentException by invalid arguments");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "companyId may not be less than 0");
    }
  }

  // positive tests

  @Test
  public void it_passes_with_valid_values() {
    Identification i = new Identification(validId, validName, validStartTime, validWaitingTime, validCompanyId);
    assertEquals(i.getId(), validId);
    assertEquals(i.getName(), validName);
    assertEquals(i.getStartTime(), validStartTime);
    assertEquals(i.getWaitingTime(), validWaitingTime);
    assertEquals(i.getCompanyId(), validCompanyId);
  }

  @Test
  public void it_passes_with_critical_low_Id() {
    Identification i = new Identification(0, validName, validStartTime, validWaitingTime, validCompanyId);
    assertEquals(i.getId(), 0);
  }

  @Test
  public void it_passes_with_critical_short_name() {
    Identification i = new Identification(validId, "A", validStartTime, validWaitingTime, validCompanyId);
    assertEquals(i.getName(), "A");
  }

  @Test
  public void it_passes_with_critical_low_StartTime() {
    Identification i = new Identification(validId, validName, 0, validWaitingTime, validCompanyId);
    assertEquals(i.getStartTime(), 0);
  }

  @Test
  public void it_passes_with_critical_low_WaitingTime() {
    Identification i = new Identification(validId, validName, validStartTime, 0, validCompanyId);
    assertEquals(i.getWaitingTime(), 0);
  }

  @Test
  public void it_passes_with_critical_high_StartTime() {
    long unixTime = System.currentTimeMillis() / 1000L;
    Identification i = new Identification(validId, validName, (int)unixTime, 0, validCompanyId);
    assertEquals(i.getStartTime(), (int)unixTime);
  }

  @Test
  public void it_passes_with_critical_high_Start_and_WaitingTime() {
    int waitingTime = 100;
    long unixTime = System.currentTimeMillis() / 1000L - waitingTime;
    Identification i = new Identification(validId, validName, (int)unixTime, waitingTime, validCompanyId);
    assertEquals(i.getStartTime(), (int)unixTime);
    assertEquals(i.getWaitingTime(), 100);
  }

}
