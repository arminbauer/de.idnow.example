package models;

import java.util.Comparator;

public class Company {

	private String companyId;
	private String companyName;
	private long slaTime;
	private float slaPercentage;
	private float currentSlaPercentage;	
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	
   
	
}

