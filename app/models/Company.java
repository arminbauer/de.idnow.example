package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Company {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    @JsonProperty("sla_time")
    private Integer slaTime;

    @JsonProperty("sla_percentage")
    private Double slaPercentage;

    @JsonProperty("current_sla_percentage")
    private Double currentSlaPercentage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSlaTime() {
        return slaTime;
    }

    public void setSlaTime(Integer slaTime) {
        this.slaTime = slaTime;
    }

    public Double getSlaPercentage() {
        return slaPercentage;
    }

    public void setSlaPercentage(Double slaPercentage) {
        this.slaPercentage = slaPercentage;
    }

    public Double getCurrentSlaPercentage() {
        return currentSlaPercentage;
    }

    public void setCurrentSlaPercentage(Double currentSlaPercentage) {
        this.currentSlaPercentage = currentSlaPercentage;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slaTime=" + slaTime +
                ", slaPercentage=" + slaPercentage +
                ", currentSlaPercentage=" + currentSlaPercentage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
