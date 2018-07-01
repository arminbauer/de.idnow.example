package util.json;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

public class JsonHelper {
    public static  <A> A parseJson(JsonNode json, Class<A> clazz) {
        // Check JSON data is defined
        if (json == null) {
            throw new IllegalArgumentException("Json is not found in request.");
        }
        // Parse JSON data
        try {
            return Json.fromJson(json, clazz);
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException("Parsing json failed. Reason: " + ex.getMessage());
        }
    }
}
