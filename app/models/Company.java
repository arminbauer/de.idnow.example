package models;

import com.fasterxml.jackson.databind.JsonNode;

public class Company {

	private int id;
	private String name;
	private long sla_seconds;
	private float sla_percent;
	private float current_sla_percent; //this month

	public static final String COMP_ID = "id";
	public static final String COMP_NAME = "name";
	public static final String COMP_SLA_TIME = "sla_time";
	public static final String COMP_SLA_PERCENT = "sla_percentage";
	public static final String COMP_CURR_SLA_PERCENT = "current_sla_percentage";
	
	public Company(int id, String name, long sla_seconds, float sla_percent, float current_sla_percent) {
		System.out.println("id:" + id + ", name:" + name + ", sla_secs:" + sla_seconds + ", sla_percent:" + sla_percent + ", curr_sla_percent:" + current_sla_percent);
		this.id = id;
		this.name = name;
		this.sla_seconds = sla_seconds;
		this.sla_percent = sla_percent;
		this.current_sla_percent = current_sla_percent;
	}
	
	public static Company fromJson(JsonNode json) {
		JsonNode id = json.path(COMP_ID);
		int idValue = id.asInt(0);
		
		JsonNode name = json.path(COMP_NAME);
		String nameValue = name.asText("");
		
		JsonNode slaSeconds = json.path(COMP_SLA_TIME);
		long slaSecondsValue = slaSeconds.asLong(0);
		
		JsonNode slaPercent = json.path(COMP_SLA_PERCENT);
		float slaPercentValue = (float)slaPercent.asDouble(0.0); //check of valid float in valid()
		
		JsonNode currSlaPercent = json.path(COMP_CURR_SLA_PERCENT);
		float currSlaPercentValue = (float)currSlaPercent.asDouble(0.0);
		
		return new Company(idValue, nameValue, slaSecondsValue, slaPercentValue, currSlaPercentValue);
	}

	public boolean valid() {
		boolean invalid = (id == 0) || (name.isEmpty()) || (sla_seconds == 0) || (sla_percent == 0.0) || (current_sla_percent == 0.0) || 
				(!Float.isFinite(sla_percent)) || (!Float.isFinite(current_sla_percent));
				
		return !invalid;
	}

	public int getid() {
		return id;
	}

}
