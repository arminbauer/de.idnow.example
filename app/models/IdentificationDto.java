package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author prasa on 03-02-2018
 * @project de.idnow.example
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentificationDto implements Serializable {

    private static final long serialVersionUID = -2623129146224024037L;

    @JsonProperty("id")
    Long id;

    @JsonProperty("name")
    String name;

    @JsonProperty("time")
    Date time;

    @JsonProperty("waiting_time")
    Integer waitingTime;

    @JsonProperty("companyid")
    Long companyId;

    public IdentificationDto() {
        super();
    }

    public IdentificationDto(Long id, String name, Date time, Integer waitingTime,
                             Long companyId) {
        super();
        this.id = id;
        this.name = name;
        this.time = time;
        this.waitingTime = waitingTime;
        this.companyId = companyId;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((companyId == null) ? 0 : companyId.hashCode());
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
        IdentificationDto other = (IdentificationDto) obj;

        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;

        if (companyId == null) {
            if (other.companyId != null)
                return false;
        } else if (!companyId.equals(other.companyId))
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
                + ", waitingTime=" + waitingTime + ", companyId=" + companyId
                + "]";
    }
}
