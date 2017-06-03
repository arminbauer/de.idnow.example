package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import identification.Company;
import org.junit.Test;
import play.libs.Json;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class CompanyJsonMapperTest {

    private CompanyJsonMapper jsonMapper = new CompanyJsonMapper();

    @Test
    public void mapsFromJson() {
        JsonNode json = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}");
        Company company = jsonMapper.fromJson(json);
        assertThat(company.getId()).isEqualTo(1);
        assertThat(company.getName()).isEqualTo("Test Bank");
        assertThat(company.getSlaTime()).isEqualTo(60);
        assertThat(company.getSlaPercentage()).isEqualByComparingTo("0.9");
        assertThat(company.getCurrentSla()).isEqualByComparingTo("0.95");
    }

    @Test
    public void mapsToJson() {
        Company company = Company.builder().id(2).name("name").slaTime(13).slaPercentage(new BigDecimal("0.82")).currentSla(new BigDecimal("0.87")).build();
        JsonNode json = jsonMapper.toJson(company);
        assertThat(json.toString()).isEqualTo("{\"id\":2,\"name\":\"name\",\"sla_time\":13,\"sla_percentage\":0.82,\"current_sla_percentage\":0.87}");
    }
}