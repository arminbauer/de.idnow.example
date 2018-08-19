package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

/**
 * Created by Talal Ahmed on 18/08/2018
 */
public class Company {

    private int id;
    private String name;

    @JsonProperty("sla_time")
    private long slaTime;

    @JsonProperty("sla_percentage")
    private double slaPercentage;

    @JsonProperty("current_sla_percentage")
    private double currentSLAPercentage;

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

    public double getCurrentSLAPercentage() {
        return currentSLAPercentage;
    }

    public void setCurrentSLAPercentage(double currentSLAPercentage) {
        this.currentSLAPercentage = currentSLAPercentage;
    }

    public static class Builder {
        Company company = new Company();

        public Builder id(int id) {
            company.setId(id);
            return this;
        }

        public Builder name(String name) {
            company.name = name;
            return this;
        }

        public Builder slaTime(long time) {
            company.slaTime = time;
            return this;
        }

        public Builder slaPercentage(double slaPercentage) {
            company.slaPercentage = slaPercentage;
            return this;
        }

        public Builder currentSLAPercentage(double currentSLAPercentage) {
            company.currentSLAPercentage = currentSLAPercentage;
            return this;
        }

        public Company get() {
            return company;
        }
    }

    @JsonIgnore
    public static Company fromJson(JsonNode json) {
        return Json.fromJson(json, Company.class);
    }

    @JsonIgnore
    public JsonNode toJson() {
        return Json.toJson(this);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slaTime=" + slaTime +
                ", slaPercentage=" + slaPercentage +
                ", currentSLAPercentage=" + currentSLAPercentage +
                '}';
    }

}
