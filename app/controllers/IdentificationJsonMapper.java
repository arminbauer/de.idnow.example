package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import identification.Identification;
import play.libs.Json;

public class IdentificationJsonMapper {

    public Identification fromJson(JsonNode json) {
        return Identification.builder()
                .id(json.findValue("id").asLong())
                .name(json.findValue("name").asText())
                .time(json.findValue("time").asLong())
                .waitingTime(json.findValue("waiting_time").asLong())
                .companyId(json.findValue("companyid").asLong())
                .build();
    }

    public JsonNode toJson(Identification identification) {
        return Json.newObject()
                .put("id", identification.getId())
                .put("name", identification.getName())
                .put("time", identification.getTime())
                .put("waiting_time", identification.getWaitingTime())
                .put("companyid", identification.getCompanyId());
    }
}
