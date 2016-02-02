package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sebastian.walter on 02.02.2016.
 */
public class AddCompanyRequest {

    private String name;

    @JsonProperty("sla_time")
    private int slaTime;

    @JsonProperty("sla_percentage")
    private float slaPercentage;

    @JsonProperty("current_sla_percentage")
    private float currentPercentage;

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

    public float getCurrentPercentage() {
        return currentPercentage;
    }

    public void setCurrentPercentage(float currentPercentage) {
        this.currentPercentage = currentPercentage;
    }
}
