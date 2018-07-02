package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Company implements Comparable<Company>{
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("sla_time")
	private long slaTime;
	
	@JsonProperty("sla_percentage")
	private float slaPercentage;
	
	@JsonProperty("current_sla_percentage")
	private float currentSlaPercentage;
	
	public Company(){
		
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public long getSlaTime() {
		return slaTime;
	}

	public void setSlaTime(long slaTime) {
		this.slaTime = slaTime;
	}

	
	public float getSlaPercentage() {
		return slaPercentage;
	}

	public void setSlaPercentage(float slaPercentage) {
		this.slaPercentage = slaPercentage;
	}

	
	public float getCurrentSlaPercentage() {
		return currentSlaPercentage;
	}

	public void setCurrentSlaPercentage(float currentSlaPercentage) {
		this.currentSlaPercentage = currentSlaPercentage;
	}
	
	@Override
	public int compareTo(Company company) {
		Float slaDifference = ((Company) company).getCurrentSlaPercentage() - ((Company) company).getSlaPercentage();
		return Float.compare(this.currentSlaPercentage - this.slaPercentage, slaDifference);
	}
}
