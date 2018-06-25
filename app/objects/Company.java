package objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Company implements Comparable<Company>{

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("sla_time")
    private int slaTime;

    @JsonProperty("sla_percentage")
    private double slaPercentage;

    @JsonProperty("current_sla_percentage")
    private double currentSlaPercentage;

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

    public int getSlaTime() {
        return slaTime;
    }

    public void setSlaTime(int slaTime) {
        this.slaTime = slaTime;
    }

    public double getSlaPercentage() {
        return slaPercentage;
    }

    public void setSlaPercentage(double slaPercentage) {
        this.slaPercentage = slaPercentage;
    }

    public double getCurrentSlaPercentage() {
        return currentSlaPercentage;
    }

    public void setCurrentSlaPercentage(double currentSlaPercentage) {
        this.currentSlaPercentage = currentSlaPercentage;
    }

    @JsonIgnore
    public double getMissingSlaPercentage() {
        return slaPercentage - currentSlaPercentage;
    }

    @Override
    public int compareTo(Company company) {
        if (Integer.compare(this.slaTime,company.slaTime) != 0){
            return Integer.compare(this.slaTime,company.slaTime);
        }
        return Double.compare((this.slaPercentage-this.currentSlaPercentage),(company.slaPercentage-company.currentSlaPercentage));
    }
}
