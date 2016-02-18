package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bduisenov on 18/02/16.
 */
public class Company {

    private String id;

    private String name;

    @JsonProperty("sla_time")
    private Integer slaTime;

    @JsonProperty("sla_percentage")
    private float slaPercentage;

    @JsonProperty("current_sla_percentage")
    private float currentSlaPercentage;

    public Company() {
    }

    public Company(String id, String name, Integer slaTime, float slaPercentage, float currentSlaPercentage) {
        this.id = id;
        this.name = name;
        this.slaTime = slaTime;
        this.slaPercentage = slaPercentage;
        this.currentSlaPercentage = currentSlaPercentage;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getSlaTime() {
        return slaTime;
    }

    public float getSlaPercentage() {
        return slaPercentage;
    }

    public float getCurrentSlaPercentage() {
        return currentSlaPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Company company = (Company) o;

        return id.equals(company.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Company{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", slaTime=" + slaTime +
                ", slaPercentage=" + slaPercentage +
                ", currentSlaPercentage=" + currentSlaPercentage +
                '}';
    }
}
