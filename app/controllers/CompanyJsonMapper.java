package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import identification.Company;
import play.libs.Json;

import java.math.BigDecimal;

public class CompanyJsonMapper {

    public Company fromJson(JsonNode json) {
        return Company.builder()
                .id(json.findValue("id").asLong())
                .name(json.findValue("name").asText())
                .slaTime(json.findValue("sla_time").asLong())
                .slaPercentage(new BigDecimal(json.findValue("sla_percentage").asText()))
                .currentSla(new BigDecimal(json.findValue("current_sla_percentage").asText()))
                .build();
    }

    public JsonNode toJson(Company company) {
        return Json.newObject()
                .put("id", company.getId())
                .put("name", company.getName())
                .put("sla_time", company.getSlaTime())
                .put("sla_percentage", company.getSlaPercentage())
                .put("current_sla_percentage", company.getCurrentSla());
    }
}
