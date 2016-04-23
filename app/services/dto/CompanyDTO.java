package services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ebajrami on 4/23/16.
 */
public class CompanyDTO {

    private int id;

    private String name;

    @JsonProperty("sla_time")
    private long slaTime;

    @JsonProperty("sla_percentage")
    private double slaPercentage;

    @JsonProperty("current_sla_percentage")
    private double currentSlaPercentage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
