package converter;

import com.fasterxml.jackson.databind.JsonNode;
import model.Identification;
import play.libs.Json;

/**
 * Created by sleski on 30.06.2015.
 */
public class IdentificationConverter {

    public static Identification jsonConvert(JsonNode json) {
        Identification identification = Json.fromJson(json, Identification.class);
        return identification;
    }

    public static JsonNode objConverter(Identification identification) {
        JsonNode jsonNode = Json.toJson(identification);
        return jsonNode;
    }
}
