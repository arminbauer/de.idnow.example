package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Identification implements Comparable<Identification>{
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("time")
	private long time;
	
	@JsonProperty("waiting_time")
	private float waitingTime;
	
	@JsonProperty("companyid")
	private int companyId;
	
	@JsonIgnore
	private Company company;

	public Identification() {
		
	}
	
	public Identification(int id, String name, long time, float waitingTime, int companyId) {
		super();
		this.id = id;
		this.name = name;
		this.time = time;
		this.waitingTime = waitingTime;
		this.companyId = companyId;
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

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public float getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(float waitingTime) {
		this.waitingTime = waitingTime;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public int compareTo(Identification identification) {
		Float waitingTime = (float)((Identification) identification).getCompany().getSlaTime() - ((Identification) identification).getWaitingTime();
		return Float.compare(((float)this.company.getSlaTime() - this.waitingTime), waitingTime);
	}
}
