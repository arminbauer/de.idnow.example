package persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "IDENTIFICATION")
@NamedQuery(name = IdentificationEntity.FINDALL_QUERY_NAME, query = "SELECT ident FROM IdentificationEntity ident")
public class IdentificationEntity implements Serializable {

	private static final long serialVersionUID = -2623129146224024037L;
    public static final String FINDALL_QUERY_NAME = "Identification.findAll";

	@Id
	@Column(name = "ID")
	Long id;

	@Column(name = "NAME")
	String name;

	@Column(name = "TIME_")
	Date time;

	@Column(name = "WAITING_TIME")
	Integer waitingTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	CompanyEntity company;

	public IdentificationEntity() {
		super();
	}

	public IdentificationEntity(Long id, String name, Date time,
			Integer waitingTime, CompanyEntity company) {
		super();
		this.id = id;
		this.name = name;
		this.time = time;
		this.waitingTime = waitingTime;
		this.company = company;
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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(Integer waitingTime) {
		this.waitingTime = waitingTime;
	}

	public CompanyEntity getCompany() {
		return company;
	}

	public void setCompany(CompanyEntity company) {
		this.company = company;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result
				+ ((waitingTime == null) ? 0 : waitingTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdentificationEntity other = (IdentificationEntity) obj;

		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;

		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;

		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;

		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;

		if (waitingTime == null) {
			if (other.waitingTime != null)
				return false;
		} else if (!waitingTime.equals(other.waitingTime))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "Identification [id=" + id + ", name=" + name + ", time=" + time
				+ ", waitingTime=" + waitingTime
				+ ((company != null) ? ", company=" + company : "") + "]";
	}

}
