package models.json;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import models.CompanyDTO;
import models.IdentificationDTO;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CompanyJsonTest {
	ObjectMapper mapper = new ObjectMapper();

	@Test
	public void readJsonTest() throws JsonParseException, JsonMappingException,
			IOException {
		String json = "{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}";
		CompanyDTO desered = mapper.readValue(json, CompanyDTO.class);
		CompanyDTO expected = new CompanyDTO(1L, "Test Bank", 60, 0.9, 0.95);
		assertEquals(expected, desered);
	}
	
	@Test(expected=JsonParseException.class)
	public void readNonJsonTest() throws JsonParseException, JsonMappingException, IOException {
		String nonJson="{\"id\": 1, \"name\": \"Test Bank\", \"sla_percentage\": , \"current_sla_percentage\": 0.95}";
		mapper.readValue(nonJson,IdentificationDTO.class);
	}
	
	@Test
	public void writeJsonTest() throws JsonParseException, JsonMappingException, IOException {
		CompanyDTO object = new CompanyDTO(1L, "Test Bank", 60, 0.9, 0.95);
		String sered=mapper.writeValueAsString(object);
		String expected="{\"id\":1,\"name\":\"Test Bank\",\"sla_time\":60,\"sla_percentage\":0.9,\"current_sla_percentage\":0.95}";
		assertEquals(expected, sered);
	}

}
