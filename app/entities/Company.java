package entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import controllers.CompanyIdResolver;

import java.util.Objects;


/**
 * Created by vld on 9/08/16.
 */

public class Company {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("sla_time")
    private int slaTime;
    @JsonProperty("sla_percentage")
    private float slaPersentage;
    @JsonProperty("current_sla_percentage")
    private float currentSlaPersentage;

    public Company() {
    }

    public Company(String id, String name, int slaTime, float slaPersentage, float currentSlaPersentage) {
        this.id = id;
        this.name = name;
        this.slaTime = slaTime;
        this.slaPersentage = slaPersentage;
        this.currentSlaPersentage = currentSlaPersentage;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSlaTime() {
        return slaTime;
    }

    public float getSlaPersentage() {
        return slaPersentage;
    }

    public float getCurrentSlaPersentage() {
        return currentSlaPersentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return slaTime == company.slaTime &&
                Float.compare(company.slaPersentage, slaPersentage) == 0 &&
                Float.compare(company.currentSlaPersentage, currentSlaPersentage) == 0 &&
                Objects.equals(id, company.id) &&
                Objects.equals(name, company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, slaTime, slaPersentage, currentSlaPersentage);
    }
}
