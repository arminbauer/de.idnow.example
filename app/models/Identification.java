package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Date;

/**
 * This class represents an identification that needs to be processed based on company's SLA.
 *
 * @author leonardo.cruz
 *
 */
public class Identification {

    private Long id;
    private String name;
    private Date time;
    @JsonProperty("waiting_time")
    private Long waitingTime;
    @JsonProperty("companyid")
    private Long companyId;

    public Identification() {
    }

    public Identification(Long id, String name, Long waitingTime, Date time, Long companyId) {
        this.id = id;
        this.name = name;
        this.waitingTime = waitingTime;
        this.time = time;
        this.companyId = companyId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getTime() {
        return time;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Long getWaitingTime() {
        return waitingTime;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Identification other = (Identification) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    

}
