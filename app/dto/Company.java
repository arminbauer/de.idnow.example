package dto;

public class Company {
	
	private Long id;
	
	private String name;
	
	private long slaTime;
	
	private Float slaPercentage;
	
	private Float currSlaPercentage;
	
	public Long getId() {
		return id;
	}
	public void setId(Long companyID) {
		this.id = companyID;
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
	public Float getSlaPercentage() {
		return slaPercentage;
	}
	public void setSlaPercentage(Float slaPercentage) {
		this.slaPercentage = slaPercentage;
	}
	public Float getCurrSlaPercentage() {
		return currSlaPercentage;
	}
	public void setCurrSlaPercentage(Float currSlaPercentage) {
		this.currSlaPercentage = currSlaPercentage;
	}


}
