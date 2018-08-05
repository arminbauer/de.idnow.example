package helpers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import play.libs.Json;

import java.util.List;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
public class JsonArrayHelper {
  public static <T> ArrayNode toJsonArray(final List<T> list) {
    final ArrayNode jsonResult = Json.newArray();
    list.forEach(e -> jsonResult.add(Json.toJson(e)));
    return jsonResult;
  }
}
