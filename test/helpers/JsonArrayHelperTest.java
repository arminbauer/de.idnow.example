package helpers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import models.Identification;
import org.junit.Assert;
import org.junit.Test;
import play.libs.Json;

import java.util.Arrays;

/**
 * @author Dmitrii Bogdanov
 * Created at 05.08.18
 */
public class JsonArrayHelperTest {

  @Test
  public void toJsonArrayCreatesCorrectlyOrderedArrayOfIntegers() {
    final ArrayNode expected = Json.newArray();
    expected.add(1);
    expected.add(2);
    expected.add(3);
    final ArrayNode nonExpected = Json.newArray();
    nonExpected.add(2);
    nonExpected.add(1);
    nonExpected.add(3);
    Assert.assertEquals(expected, JsonArrayHelper.toJsonArray(Arrays.asList(1, 2, 3)));
    Assert.assertNotEquals(nonExpected, JsonArrayHelper.toJsonArray(Arrays.asList(1, 2, 3)));
  }

  @Test
  public void toJsonArrayCreatesCorrectlyOrderedArrayOfObjects() {
    final Identification obj1 = new Identification();
    final Identification obj2 = new Identification();
    final Identification obj3 = new Identification();
    obj1.setId(1L);
    obj2.setId(2L);
    obj3.setId(3L);
    final ArrayNode expected = Json.newArray();
    expected.add(Json.toJson(obj1));
    expected.add(Json.toJson(obj2));
    expected.add(Json.toJson(obj3));
    Assert.assertEquals(expected, JsonArrayHelper.toJsonArray(Arrays.asList(obj1, obj2, obj3)));
  }
}