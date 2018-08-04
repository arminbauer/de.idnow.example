import play.libs.Json;
import play.libs.ws.WSResponse;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
public class TestHelper {
  public static <T> T parseObjectFromResponse(final WSResponse response, final Class<T> clazz) {
    return Json.fromJson(Json.parse(response.getBody()), clazz);
  }
}
