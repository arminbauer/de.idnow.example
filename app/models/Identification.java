package models;

import java.time.Instant;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.Json;

public class Identification {
	
	private int id;
	private String userName;
	private Instant requestStarted;
	private long waitingSeconds;
	private int companyId;

	public static final String IDENT_ID = "id";
	public static final String IDENT_NAME = "name";
	public static final String IDENT_TIME = "time";
	public static final String IDENT_WAIT_TIME = "waiting_time";
	public static final String IDENT_COMPANY_ID = "companyid";
	
	public Identification(int id, String userName, Instant requestStarted, long waitingSeconds, int companyId) {
		//System.out.println("id:" + id + ", name:" + userName + ", started:" + requestStarted.toString() + ", wait:" + waitingSeconds + ", company:" + companyId);
		
		this.id = id;
		this.userName = userName;
		this.requestStarted = requestStarted;
		this.waitingSeconds = waitingSeconds;
		this.companyId = companyId;
	}

	public static Identification fromJson(JsonNode json) {
		JsonNode id = json.path(IDENT_ID);
		int idValue = id.asInt(0);
		
		JsonNode name = json.path(IDENT_NAME);
		String nameValue = name.asText("");
		
		JsonNode time = json.path(IDENT_TIME);
		long timeValue = time.asLong(0);
		
		JsonNode waitingTime = json.path(IDENT_WAIT_TIME);
		long waitingTimeValue = waitingTime.asLong(0);
		
		JsonNode companyId = json.path(IDENT_COMPANY_ID);
		int companyidValue = companyId.asInt(0);
		
		return new Identification(idValue, nameValue, Instant.ofEpochSecond(timeValue), waitingTimeValue, companyidValue);
	}

	public boolean valid() {
		boolean invalid =  (id == 0) || (userName.isEmpty()) || (requestStarted.getEpochSecond() == 0) || (waitingSeconds == 0) || (companyId == 0);
		return (!invalid);
	}

	public JsonNode toJson() {
		ObjectNode identification = Json.newObject();
		identification.put(IDENT_ID, id);
		identification.put(IDENT_NAME, userName);
		identification.put(IDENT_TIME, requestStarted.getEpochSecond());
		identification.put(IDENT_WAIT_TIME, waitingSeconds);
		identification.put(IDENT_COMPANY_ID, companyId);
		return identification;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Identification)) {
			return false;
		}
		Identification other = (Identification) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	

}