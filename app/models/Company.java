package models;

public class Company {
	private int id;
	private String name;
	private int slaTime;
	private float slaPercentage;
	private float currentSlaPercentage;

	public Company(int id, String name, int slaTime, float slaPercentage) {
		this.id = id;
		this.name = name;
		this.slaTime = slaTime;
		this.slaPercentage = slaPercentage;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", slaTime=" + slaTime + ", slaPercentage=" + slaPercentage
				+ ", currentSlaPercentage=" + currentSlaPercentage + "]";
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getSlaTime() {
		return slaTime;
	}

	public float getSlaPercentage() {
		return slaPercentage;
	}

	public float getCurrentSlaPercentage() {
		return currentSlaPercentage;
	}

	public void setCurrentSlaPercentage(float currentSlaPercentage) {
		this.currentSlaPercentage = currentSlaPercentage;
	}

}
