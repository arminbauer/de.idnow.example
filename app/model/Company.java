package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Company {

    private int id;
    private String name;

    @JsonProperty("sla_time")
    private int slaTime;

    @JsonProperty("sla_percentage")
    private Float slaPercentage;

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

    public Float getSlaPercentage() {
        return slaPercentage;
    }

    public void setSlaPercentage(Float slaPercentage) {
        this.slaPercentage = slaPercentage;
    }

    public float getCurrentSlaPercentage() {
        return currentSlaPercentage;
    }

    public void setCurrentSlaPercentage(float currentSlaPercentage) {
        this.currentSlaPercentage = currentSlaPercentage;
    }
}
