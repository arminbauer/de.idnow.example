package models;

import javax.persistence.Entity;

import javax.persistence.Id;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class for a company.
 *
 * @author Markus Panholzer <markus.panholzer@eforce21.com>
 * @since 31.09.2017
 */
@Entity
public class Company extends Model {

	/** The unique ID of the company. */
	@Id
	private String id;

	/** The name of the company. */
	private String name;

	/** The SLA (Service Level Agreement) time of this company in seconds. */
	@JsonProperty("sla_time")
	private long slaTime;

	/**
	 * The SLA (Service Level Agreement) percentage of this company as float.
	 */
	@JsonProperty("sla_percentage")
	private float slaPercentage;

	/**
	 * The current SLA percentage of this company in this month (e.g. 0.95 would
	 * mean that IDnow achieved an SLA_percentage of 95% for this company for
	 * this month. If this is lower than SLA_percentage at the end of the month,
	 * we would not have reached to agreed SLA).
	 */
	@JsonProperty("current_sla_percentage")
	private float currentSlaPercentage;

	/** Finder instance for retrieving a company from the database. */
	private static Finder<String, Company> find = new Finder<>(Company.class);

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

	public long getSlaTime() {
		return slaTime;
	}

	public void setSlaTime(long slaTime) {
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

	public static Company find(String id) {
		if (id != null) {
			return find.byId(id);
		}
		return null;
	}

}
