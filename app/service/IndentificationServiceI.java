package service;

import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;

public interface IndentificationServiceI {

	void getOptimalOrder(JsonNode json);

}