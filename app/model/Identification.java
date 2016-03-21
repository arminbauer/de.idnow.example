package model;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static utils.ValidationUtils.isPositive;
import static utils.ValidationUtils.isPositiveOrZero;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import errorhandling.NotFoundException;
import org.hibernate.validator.constraints.NotEmpty;
import play.db.jpa.JPA;
import play.libs.Json;
import repository.CompanyRepository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.ValidationException;

/**
 * An identification which is a request of a user to be identified by IDnow
 */
@Entity
@Table(name = "identifications")
public class Identification {

	/**
	 * The unique ID of the identification
	 */
	@Id
	private Long id;

	/**
	 * Name of the user
	 */
	@Column(length = 128, nullable = false)
	@NotEmpty
	private String name;

	/**
	 * The time when this identification request was started by the user (Unix format)
	 */
	@Column(nullable = false)
	@JsonProperty("time")
	private Long time;

	/**
	 * The current waiting time of the identification in seconds (since the user started)
	 */
	@Column(nullable = false, name = "waiting_time")
	@JsonProperty("waiting_time")
	private Integer waitingTime;

	/**
	 * The ID of the company to which this identification belongs
	 */
	@JsonProperty("company_id")
	private Long companyId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "company_id")
	private Company company;

	@Transient
	@JsonIgnore
	private CompanyRepository companyRepository = new CompanyRepository();

	public static Identification fromJson(JsonNode json) {
		Identification identification = Json.fromJson(json, Identification.class);
		identification.validate();
		return identification;
	}

	public void validate() {
		if (!isPositive(id)) {
			throw new ValidationException("id field must be greater then zero for object " + this);
		}
		if (isEmpty(name)) {
			throw new ValidationException("name field cannot be empty for object " + this);
		}
		if (!isPositiveOrZero(time)) {
			throw new ValidationException("time field must be non negative for object "  + this);
		}
		if (!isPositiveOrZero(waitingTime)) {
			throw new ValidationException("waiting_time field must be non negative for object "  + this);
		}
		if (!isPositive(companyId)) {
			throw new ValidationException("company_id field must be greater then zero for object "  + this);
		}
		if (!companyRepository.exists(companyId)) {
			throw new NotFoundException("No company found with id " + companyId + " for object " + this);
		}
	}

	public void save() {
		if (company == null) {
			company = getCompany();
		}
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

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Integer getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(Integer waitingTime) {
		this.waitingTime = waitingTime;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Company getCompany() {
		if (company == null) {
			return companyRepository.findById(companyId);
		}
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setCompanyRepository(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	@Override
	public String toString() {
		return "Identification{" +
				"id=" + id +
				", name='" + name + '\'' +
				", time=" + time +
				", waitingTime=" + waitingTime +
				", companyId=" + companyId +
				", company=" + company +
				'}';
	}
}
