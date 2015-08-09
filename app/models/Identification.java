package models;

import java.util.*;

import java.time.Instant;

 
public class  Identification implements Comparable{


private String id;
private String name;
private long time;
private long waiting_time;
private String companyid;


	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public long getWaiting_time() {
		long unixTimestamp = Instant.now().getEpochSecond();
		return unixTimestamp-time;
	}
	public void setWaiting_time(long waiting_time) {
		this.waiting_time = waiting_time;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public int compareTo(Object obj){ 
		Identification identificationToCompare = (Identification)obj;
		Company companyToCompare =  Models.companyList.get(identificationToCompare.getCompanyid());
		Company company = Models.companyList.get(companyid);

		//Sorting logic: sla_time, waiting_time, sla_percentage

		if(company.getSla_time() == companyToCompare.getSla_time()){
			if(this.waiting_time == identificationToCompare.getWaiting_time()){
				if(company.getSla_percentage()>companyToCompare.getSla_percentage()){
					return 1;
				}else{
					return -1;
				}
			}else {
				if(this.waiting_time > identificationToCompare.getWaiting_time()){
					return 1;
				}else{
					return -1;
				}
			}
		}else{
			if(company.getSla_time() < companyToCompare.getSla_time()){
			return 1;
			}else{
				return-1;
			}
		}
		
	}

}


