package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import identification.Identification;
import org.junit.Test;
import play.libs.Json;

import static org.assertj.core.api.Assertions.assertThat;

public class IdentificationJsonMapperTest {

    private IdentificationJsonMapper jsonMapper = new IdentificationJsonMapper();

    @Test
    public void mapsFromJson() {
        JsonNode json = Json.parse("{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}");
        Identification identification = jsonMapper.fromJson(json);
        assertThat(identification.getId()).isEqualTo(1);
        assertThat(identification.getName()).isEqualTo("Peter Huber");
        assertThat(identification.getTime()).isEqualTo(1435667215);
        assertThat(identification.getWaitingTime()).isEqualTo(10);
        assertThat(identification.getCompanyId()).isEqualTo(1);
    }

    @Test
    public void mapsToJson() {
        Identification identification = Identification.builder().id(3).name("Jacek Bilski").time(42).waitingTime(2).companyId(13).build();
        JsonNode json = jsonMapper.toJson(identification);
        assertThat(json.toString()).isEqualTo("{\"id\":3,\"name\":\"Jacek Bilski\",\"time\":42,\"waiting_time\":2,\"companyid\":13}");
    }
}