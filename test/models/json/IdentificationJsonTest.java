package models.json;

import java.io.IOException;
import java.util.Date;

import models.IdentificationDTO;

import org.junit.Test;

import static org.junit.Assert.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class IdentificationJsonTest {
	ObjectMapper mapper = new ObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);

	@Test
	public void readJsonTest() throws JsonParseException, JsonMappingException, IOException {
		String json="{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}";
		IdentificationDTO desered = mapper.readValue(json,IdentificationDTO.class);
		IdentificationDTO expected = new IdentificationDTO(1L,"Peter Huber",new Date(1435667215),10,1L);
		assertEquals(expected, desered);
	}
	
	@Test(expected=JsonParseException.class)
	public void readNonJsonTest() throws JsonParseException, JsonMappingException, IOException {
		String nonJson="{\"id\": 1, \"name\": , \"waiting_time\": 10, \"companyid\"}";
		mapper.readValue(nonJson,IdentificationDTO.class);
	}
	
	@Test
	public void writeJsonTest() throws JsonParseException, JsonMappingException, IOException {
		IdentificationDTO object = new IdentificationDTO(1L,"Peter Huber",new Date(1435667215),10,1L);
		String sered=mapper.writeValueAsString(object);
		String expected="{\"id\":1,\"name\":\"Peter Huber\",\"time\":1435667215,\"waiting_time\":10,\"companyid\":1}";
		assertEquals(expected, sered);
	}
	
}
