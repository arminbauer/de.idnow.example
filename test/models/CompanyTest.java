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

public class CompanyTest {

  private static final int    validId = 1;
  private static final String validName = "Valid Name";
  private static final int    validSLATime = 60;
  private static final float  validSLAPercentage = 0.9f;
  private static final float  validCurrentSLAPercentage = 0.85f;

  // Constructor tests

  // Negative tests
  @Test
  public void it_raises_an_IllegalArgumentException_on_invalid_id() {
    try {
      Company c = new Company(-1, validName, validSLATime, validSLAPercentage);
      fail("Expected IllegalArgumentException by invalid arguments");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "id may not be less than 0");
    }
  }

  @Test
  public void it_raises_an_IllegalArgumentException_on_invalid_null_name() {
    try {
      Company c = new Company(validId, null, validSLATime, validSLAPercentage);
      fail("Expected IllegalArgumentException by invalid arguments");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "name may not be null or empty");
    }
  }

  @Test
  public void it_raises_an_IllegalArgumentException_on_invalid_empty_name() {
    try {
      Company c = new Company(validId, "", validSLATime, validSLAPercentage);
      fail("Expected IllegalArgumentException by invalid arguments");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "name may not be null or empty");
    }
  }

  @Test
  public void it_raises_an_IllegalArgumentException_on_invalid_SLATime() {
    try {
      Company c = new Company(validId, validName, -1, validSLAPercentage);
      fail("Expected IllegalArgumentException by invalid arguments");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "slaTime may not be less than 0");
    }
  }

  @Test
  public void it_raises_an_IllegalArgumentException_on_invalid_negative_SLAPercentage() {
    try {
      Company c = new Company(validId, validName, validSLATime, -0.5f);
      fail("Expected IllegalArgumentException by invalid arguments");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "slaPercentage needs to be within [0, 1]");
    }
  }

  @Test
  public void it_raises_an_IllegalArgumentException_on_invalid_too_high_SLAPercentage() {
    try {
      Company c = new Company(validId, validName, validSLATime, 1.00001f);
      fail("Expected IllegalArgumentException by invalid arguments");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "slaPercentage needs to be within [0, 1]");
    }
  }

  @Test
  public void it_raises_an_IllegalArgumentException_on_invalid_too_low_CurrentSLAPercentage() {
    try {
      Company c = new Company(validId, validName, validSLATime, validSLAPercentage);
      c.setCurrentSLAPercentage(-0.5f);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "currentSLAPercentage needs to be within [0, 1]");
    }
  }

  @Test
  public void it_raises_an_IllegalArgumentException_on_invalid_too_high_CurrentSLAPercentage() {
    try {
      Company c = new Company(validId, validName, validSLATime, validSLAPercentage);
      c.setCurrentSLAPercentage(1.1f);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "currentSLAPercentage needs to be within [0, 1]");
    }
  }

  // positive tests

  @Test
  public void it_passes_with_valid_values() {
    Company c = new Company(validId, validName, validSLATime, validSLAPercentage);
    c.setCurrentSLAPercentage(validCurrentSLAPercentage);
    assertEquals(c.getId(), validId);
    assertEquals(c.getName(), validName);
    assertEquals(c.getSLATime(), validSLATime);
    assertEquals(c.getSLAPercentage(), validSLAPercentage, 0.00001f);
    assertEquals(c.getCurrentSLAPercentage(), validCurrentSLAPercentage, 0.00001f);
  }

  @Test
  public void it_passes_with_critical_low_Id() {
    Company c = new Company(0, validName, validSLATime, validSLAPercentage);
    assertEquals(c.getId(), 0);
  }

  @Test
  public void it_passes_with_critical_short_name() {
    Company c = new Company(validId, "A", validSLATime, validSLAPercentage);
    assertEquals(c.getName(), "A");
  }

  @Test
  public void it_passes_with_critical_low_SLATime() {
    Company c = new Company(validId, validName, 0, validSLAPercentage);
    assertEquals(c.getSLATime(), 0);
  }

  @Test
  public void it_passes_with_critical_low_SLAPercentage() {
    Company c = new Company(validId, validName, validSLATime, 0.0f);
    assertEquals(c.getSLAPercentage(), 0.0f, 0.00001f);
  }

  @Test
  public void it_passes_with_critical_high_SLAPercentage() {
    Company c = new Company(validId, validName, validSLATime, 1.0f);
    assertEquals(c.getSLAPercentage(), 1.0f, 0.00001f);
  }

  @Test
  public void it_passes_with_critical_low_CurrentSLAPercentage() {
    Company c = new Company(validId, validName, validSLATime, validSLAPercentage);
    c.setCurrentSLAPercentage(0.0f);
    assertEquals(c.getCurrentSLAPercentage(), 0.0f, 0.00001f);
  }

  @Test
  public void it_passes_with_critical_high_CurrentSLAPercentage() {
    Company c = new Company(validId, validName, validSLATime, validSLAPercentage);
    c.setCurrentSLAPercentage(1.0f);
    assertEquals(c.getCurrentSLAPercentage(), 1.0f, 0.00001f);
  }
}
