package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.data.validation.ValidationError;
import play.libs.Json;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nick on 14.05.16.
 */
public class Errors {


    public static ObjectNode error(String errMsg) {
        return Json.newObject().put("errorMsg", errMsg);
    }

    public static JsonNode validationError(List<ValidationError> errors) {
        ArrayNode errorsAsJson = Json.newArray().
                addAll(errors.stream().
                        map(validationError -> Json.newObject().set(validationError.key(), Json.toJson(validationError.messages()))).
                        collect(Collectors.toList()));
        return error("Validation failed").put("errorType", "validationFailed").set("details", errorsAsJson);


    }
}
