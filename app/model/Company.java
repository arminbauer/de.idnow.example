package model;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static utils.ValidationUtils.isPositive;
import static utils.ValidationUtils.isPositiveOrZero;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import play.db.jpa.JPA;
import play.libs.Json;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.ValidationException;

/**
 * A company, which is a customer of IDnow
 */
@Entity
@Table(name = "companies")
public class Company {

	public static final String CURRENT_SLA_PERCENTAGE = "current_sla_percentage";
	public static final String SLA_PERCENTAGE = "sla_percentage";
	public static final String SLA_TIME = "sla_time";
	/**
	 * The unique ID of the company
	 */
	@Id
	private Long id;

	/**
	 * The name of the company
	 */
	@Column(length = 128, nullable = false)
	private String name;

	/**
	 * The SLA (Service Level Agreement) time of this company in seconds
	 */
	@Column(nullable = false, name = SLA_TIME)
	@JsonProperty(SLA_TIME)
	private Integer slaTime;

	/**
	 * The SLA (Service Level Agreement) percentage of this company as float
	 */
	@Column(nullable = false, name = SLA_PERCENTAGE)
	@JsonProperty(SLA_PERCENTAGE)
	private Double slaPercentage;

	/**
	 * The current SLA percentage of this company in this month
	 */
	@Column(nullable = false, name = CURRENT_SLA_PERCENTAGE)
	@JsonProperty(CURRENT_SLA_PERCENTAGE)
	private Double currentSlaPercentage;

	public static Company fromJson(JsonNode json) {
		Company company = Json.fromJson(json, Company.class);
		company.validate();
		return company;
	}

	public void validate() {
		if (!isPositive(id)) {
			throw new ValidationException("id field must be greater then zero for object " + this);
		}
		if (isEmpty(name)) {
			throw new ValidationException("name field cannot be empty for object " + this);
		}
		if (!isPositiveOrZero(slaTime)) {
			throw new ValidationException("sla_time field must be non negative for object " + this);
		}
		if (!isPositiveOrZero(slaPercentage)) {
			throw new ValidationException("sla_percentage field must be non negative for object " + this);
		}
		if (!isPositiveOrZero(currentSlaPercentage)) {
			throw new ValidationException("current_sla_percentage field must be non negative for object " + this);
		}
	}

	public void save() {
		JPA.em().persist(this);
	}
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

	public Double getSlaPercentage() {
		return slaPercentage;
	}

	public void setSlaPercentage(Double slaPercentage) {
		this.slaPercentage = slaPercentage;
	}

	public Double getCurrentSlaPercentage() {
		return currentSlaPercentage;
	}

	public void setCurrentSlaPercentage(Double currentSlaPercentage) {
		this.currentSlaPercentage = currentSlaPercentage;
	}

	@Override
	public String toString() {
		return "Company{" +
				"id=" + id +
				", name='" + name + '\'' +
				", slaTime=" + slaTime +
				", slaPercentage=" + slaPercentage +
				", currentSlaPercentage=" + currentSlaPercentage +
				'}';
	}
}
