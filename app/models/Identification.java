package models;

import java.beans.Transient;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Identification implements Comparable<Identification> {
	private int id;
	private String name;
	private long time;
	@JsonProperty("waiting_time")
	private int waitingTime;
	@JsonProperty("companyid")
	private int companyId;
	
	
	Company company;
	@Transient
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
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
	public int getWaitingTime() {
		return waitingTime;
	}
	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	@Override
	public int hashCode(){
		return id*3;
	}
	@Override
	public String toString(){
		return "[Identification:"+id+":"+name+"]";
	}
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Identification)){
			return false;
		}
		Identification iObj = (Identification)o;
		return id == iObj.getId();
	}
	
	/*
	 * calTotalWorkMustFinishPerTimeUnit usage
	 * Company 1 with SLA_time=60, SLA_percentage=0.9, Current_SLA_percentage=0.95
	 * Identification 1 belonging to Company1: Waiting_time=30
	 * => we have 30 time unit to finish 0.05 work
	 * => 1 time unit, we have to finish (0.05/30) work
	 * this value is more large the identification is more urgent
	 * */
	public float calTotalWorkMustFinishPerTimeUnit(){
		float restOfWork = 1-company.getCurrentSLAPercentage();
		int restOfTime = company.getSLATime() - waitingTime;
		return restOfWork/restOfTime;
	}
	
	@Override
	public int compareTo(Identification o) {
		int result=0;
		if(companyId != o.getCompanyId()){
			result = Float.compare(calTotalWorkMustFinishPerTimeUnit(),o.calTotalWorkMustFinishPerTimeUnit());
		}
		if(result==0){
			result = Integer.compare(waitingTime,o.getWaitingTime());
		}
		if(result!=0){
			/*more urgent will display firstly*/
			result*=-1;
		}
		return result;
	}
}