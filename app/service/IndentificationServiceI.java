package service;

import play.libs.Json;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import models.*;

public interface IndentificationServiceI {

	 List<Identification>  getOptimalOrder(JsonNode json);

}