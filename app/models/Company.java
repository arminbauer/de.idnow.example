package models;

public class Company {
	
	private Long id;
	private String name;
	private Integer slaTime;
	private float slaPercentage;
	private float currentSlaPercentage;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSlaTime() {
		return slaTime;
	}
	public void setSlaTime(Integer slaTime) {
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
