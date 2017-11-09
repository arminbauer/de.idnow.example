package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

import java.time.ZoneId;
import java.util.List;

/**
 * Created by Florian Schmidt on 07.11.2017.
 */
public class JsonSerializer {
    private static JsonNode serializeIdentification(Identification identification) {
        IdentificationJSON identificationJSON = new IdentificationJSON();
        identificationJSON.setId(identification.toString());
        identificationJSON.setName(identification.getName());
        identificationJSON.setTime(identification.getTime().atZone(ZoneId.systemDefault()).toEpochSecond());
        identificationJSON.setWaitingTime(identification.getWaitingTime());
        identificationJSON.setCompanyId(identification.getCompanyId().toString());

        return serializeIdentification(identificationJSON);
    }

    private static JsonNode serializeIdentification(IdentificationJSON identificationJSON) {
        ObjectNode node = Json.newObject();
        node.put("id", identificationJSON.getId());
        node.put("name", identificationJSON.getName());
        node.put("time", identificationJSON.getTime());
        node.put("waiting_time",  identificationJSON.getWaitingTime());
        node.put("company_id", identificationJSON.getCompanyId());
        return node;
    }

    public static ArrayNode serializeIdentifications(List<Identification> identifications) {
        ArrayNode identificationJsonArray = Json.newArray();

        identifications.forEach(identification -> {
            JsonNode json = JsonSerializer.serializeIdentification(identification);
            identificationJsonArray.add(json);
        });

        return identificationJsonArray;
    }
}
