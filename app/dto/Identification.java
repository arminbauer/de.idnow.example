package dto;

import java.io.Serializable;

public class Identification implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5105917456971678145L;

	private Long id;
	
	private String name;
	
	private long time;
	
	private long waitingTime;
	
	private Long companyId;
	
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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
	public long getWaitingTime() {
		return waitingTime;
	}
	public void setWaitingTime(long waitingTime) {
		this.waitingTime = waitingTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	

}
