package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author prasa on 03-02-2018
 * @project de.idnow.example
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyDto implements Serializable {

    private static final long serialVersionUID = 4874274128501308L;

    @JsonProperty("id")
    Long id;

    @JsonProperty("name")
    String name;

    @JsonProperty("sla_time")
    Integer slaTime;

    @JsonProperty("sla_percentage")
    Double slaPercentage;

    @JsonProperty("current_sla_percentage")
    Double currentSlaPercentage;

    public CompanyDto() {
        super();
    }

    public CompanyDto(Long id, String name, Integer slaTime, Double slaPercentage,
                      Double currentSlaPercentage) {
        super();
        this.id = id;
        this.name = name;
        this.slaTime = slaTime;
        this.slaPercentage = slaPercentage;
        this.currentSlaPercentage = currentSlaPercentage;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((currentSlaPercentage == null) ? 0 : currentSlaPercentage
                .hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + ((slaPercentage == null) ? 0 : slaPercentage.hashCode());
        result = prime * result + ((slaTime == null) ? 0 : slaTime.hashCode());
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
        CompanyDto other = (CompanyDto) obj;
        if (currentSlaPercentage == null) {
            if (other.currentSlaPercentage != null)
                return false;
        } else if (!currentSlaPercentage.equals(other.currentSlaPercentage))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (slaPercentage == null) {
            if (other.slaPercentage != null)
                return false;
        } else if (!slaPercentage.equals(other.slaPercentage))
            return false;
        if (slaTime == null) {
            if (other.slaTime != null)
                return false;
        } else if (!slaTime.equals(other.slaTime))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Company [id=" + id + ", name=" + name + ", slaTime=" + slaTime
                + ", slaPercentage=" + slaPercentage
                + ", currentSlaPercentage=" + currentSlaPercentage + "]";
    }
}
