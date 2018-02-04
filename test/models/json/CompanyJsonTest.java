package models.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.CompanyDto;
import models.IdentificationDto;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


/**
 * @author prasa on 04-02-2018
 * @project de.idnow.example
 */
public class CompanyJsonTest {
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void readJsonTest() throws JsonParseException, JsonMappingException,
            IOException {
        String json = "{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}";
        CompanyDto desered = mapper.readValue(json, CompanyDto.class);
        CompanyDto expected = new CompanyDto(1L, "Test Bank", 60, 0.9, 0.95);
        assertEquals(expected, desered);
    }

    @Test(expected = JsonParseException.class)
    public void readNonJsonTest() throws JsonParseException, JsonMappingException, IOException {
        String nonJson = "{\"id\": 1, \"name\": \"Test Bank\", \"sla_percentage\": , \"current_sla_percentage\": 0.95}";
        mapper.readValue(nonJson, IdentificationDto.class);
    }

    @Test
    public void writeJsonTest() throws JsonParseException, JsonMappingException, IOException {
        CompanyDto object = new CompanyDto(1L, "Test Bank", 60, 0.9, 0.95);
        String sered = mapper.writeValueAsString(object);
        String expected = "{\"id\":1,\"name\":\"Test Bank\",\"sla_time\":60,\"sla_percentage\":0.9,\"current_sla_percentage\":0.95}";
        assertEquals(expected, sered);
    }
}
