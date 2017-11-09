package models;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Created by Florian Schmidt on 07.11.2017.
 */
public class CompanyJSON {
    private String id;

    private String name;

    @JsonProperty("sla_time")
    private int slaTime;

    @JsonProperty("sla_percentage")
    private float slaPercentage;

    @JsonProperty("current_sla_percentage")
    private float currentSlaPercentage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
