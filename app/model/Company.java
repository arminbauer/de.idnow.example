package model;

/**
 * Id: The unique ID of the company
 * Name: The name of the company
 * SLA_time: The SLA (Service Level Agreement) time of this company in seconds
 * SLA_percentage: The SLA (Service Level Agreement) percentage of this company as float
 * Current_SLA_percentage: The current SLA percentage of this company in this month (e.g. 0.95 would mean that IDnow achieved an SLA_percentage of 95% for this company for this month. If this is lower than SLA_percentage at the end of the month, we would not have reached to agreed SLA)
 * 
 * @author comdotlinux
 *
 */

public class Company {
	private long id;
	private String name;
	private long sla_time;
	private float sla_percentage;
	private float current_sla_percentage;
	
	
	
	public Company() {
	}
	
	public Company(long id, String name, long sla_time, float sla_percentage, float current_sla_percentage) {
		this.id = id;
		this.name = name;
		this.sla_time = sla_time;
		this.sla_percentage = sla_percentage;
		this.current_sla_percentage = current_sla_percentage;
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
	public long getSla_time() {
		return sla_time;
	}
	public void setSla_time(long sla_time) {
		this.sla_time = sla_time;
	}
	public float getSla_percentage() {
		return sla_percentage;
	}
	public void setSla_percentage(float sla_percentage) {
		this.sla_percentage = sla_percentage;
	}
	public float getCurrent_sla_percentage() {
		return current_sla_percentage;
	}
	public void setCurrent_sla_percentage(float current_sla_percentage) {
		this.current_sla_percentage = current_sla_percentage;
	}

	
	
}
