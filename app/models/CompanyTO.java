package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Wolfgang Ostermeier on 09.10.2017.
 *
 * CompanyTO class that gets parsed from JSON
 */
public class CompanyTO {

    private int id;

    private String name;

    @JsonProperty("sla_time")
    private int slaTime;

    @JsonProperty("sla_percentage")
    private float slaPercentage;

    @JsonProperty("current_sla_percentage")
    private float currentSlaPercentage;

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

    public int getSlaTime() {
        return slaTime;
    }

    public void setSlaTime(int slaTime) {
        this.slaTime = slaTime;
    }

    public float getSlaPercentage() {
        return slaPercentage;
    }

    public void setSlaPercentage(float slaPercentage) {
        this.slaPercentage = slaPercentage;
    }

    public float getCurrentSlaPercentage() {
        return currentSlaPercentage;
    }

    public void setCurrentSlaPercentage(float currentSlaPercentage) {
        this.currentSlaPercentage = currentSlaPercentage;
    }

}
