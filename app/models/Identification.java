package models;

import java.time.Instant;
import java.util.UUID;

public class Identification {
	
	private int id;
	private String name;
	private long time;	
	private long waitingTime;
	private Company company;	
	
	
	public Identification(int id, String name, Company company ){
		this(id, name,Instant.now().getEpochSecond(), Instant.now().getEpochSecond(), company );				
	}
	
	public Identification(int id, String name, long waitingTime, Company company ){
		this(id, name,Instant.now().getEpochSecond(), waitingTime, company );				
	}
	
	public Identification(int id, String name, long time, long waitingTime, Company company ){
		this.id = id;
		this.time = time; //Instant.now().getEpochSecond();
		this.waitingTime = waitingTime;
		this.name = name;
		this.company = company;		
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public long getTime() {
		return time;
	}
	public long getWaitingTime() {
		return waitingTime;
	}
	public void setWaitingTime(long waitingTime) {
		this.waitingTime = waitingTime;
	}

	public Company getCompany() {
		return company;
	}

	@Override
	public String toString() {
		return "Identification [id=" + id + ", name=" + name + ", time=" + time + ", waitingTime=" + waitingTime + "]";
	}

	
	/*
	@Override
	public String toString() {		
		//return "name=" + name+" -> ["+ waitingTime+" ," + ((company.getSlaPercentage()*100) - (company.getCurrentSlaPercentage()*100)) +", "+ company.getName()+":"+company.getSlaTime()+" ]";
		return "name=" + name+" -> ["+ waitingTime+" ,"+( company.getCurrentSlaPercentage() - company.getSlaPercentage())+ " : " + company.getSlaPercentage() +","+ company.getCurrentSlaPercentage() +", "+ company.getName()+":"+company.getSlaTime()+" ]";
	}
	*/
	
	
	

}
