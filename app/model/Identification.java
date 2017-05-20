package model;

import java.sql.Date;
import java.sql.Time;

/**
 * Id: The unique ID of the identification
 * Name: Name of the user
 * Time: The time when this identification request was started by the user (Unix format)
 * Waiting_time: The current waiting time of the identification in seconds (since the user started)
 * Companyid: The ID of the company to which this identification belongs
 * 
 * @author comdotlinux
 *
 */

public class Identification {
	private long id;
	private String name;
	private long time; // later change to time
	private long waiting_time;
	private long companyid;
	
	
	public Identification() {
	}
	
	
	
	public Identification(long id, String name, long time, long waiting_time, long companyid) {
		this.id = id;
		this.name = name;
		this.time = time;
		this.waiting_time = waiting_time;
		this.companyid = companyid;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
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
		return waiting_time;
	}
	public void setWaiting_time(long waiting_time) {
		this.waiting_time = waiting_time;
	}
	public long getCompanyid() {
		return companyid;
	}
	public void setCompanyid(long companyid) {
		this.companyid = companyid;
	}

}
