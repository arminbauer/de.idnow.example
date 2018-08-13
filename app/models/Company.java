package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents company that demands identifications.
 *
 * @author leonardo.cruz
 *
 */
public class Company {


    private Long id;
    private String name;
    @JsonProperty("sla_time")
    private Long slaTime;
    @JsonProperty("sla_percentage")
    private Float slaPercentage;
    @JsonProperty("current_sla_percentage")
    private Float currentSlaPercentage;

    public Company() {
    }

    public Company(Long id, String name, Long slaTime, Float slaPercentage, Float currentSlaPercentage) {
        this.id = id;
        this.name = name;
        this.slaTime = slaTime;
        this.slaPercentage = slaPercentage;
        this.currentSlaPercentage = currentSlaPercentage;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getSlaTime() {
        return slaTime;
    }

    public Float getSlaPercentage() {
        return slaPercentage;
    }

    public Float getCurrentSlaPercentage() {
        return currentSlaPercentage;
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
		Company other = (Company) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
}
