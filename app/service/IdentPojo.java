package service;

public class IdentPojo {
	
	private Long id;
	
	private String name;
	
	private long time;
	
	private long waitingTime;
	
	private Long remainingServiceTime;
	
	private Long companyId;
	
	private Float slaPercentageDelta;
	
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
	public Float getSlaPercentageDelta() {
		return slaPercentageDelta;
	}
	public void setSlaPercentageDelta(Float slaPercentageDelta) {
		this.slaPercentageDelta = slaPercentageDelta;
	}
	public Long getRemainingServiceTime() {
		return remainingServiceTime;
	}
	public void setRemainingServiceTime(Long remainingServiceTime) {
		this.remainingServiceTime = remainingServiceTime;
	}

}
