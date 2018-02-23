package models;

import javax.validation.constraints.NotNull;
import java.util.UUID;

// No builder pattern for now, for simplicity.
public class Company {

    // Assumed constraint.
    @NotNull(message = "Id cannot be null.")
    private UUID id;

    // Assumed constraint.
    @NotNull(message = "Name cannot be null.")
    private String name;

    private Long slaTime; // No validation currently.
    private Float slaPercentage; // No validation currently.
    private Float currentSLAPercentage; // No validation currently.

    public Company() {
    }

    public Company(UUID id, String name, Long slaTime, Float slaPercentage, Float currentSLAPercentage) {
        this.id = id;
        this.name = name;
        this.slaTime = slaTime;
        this.slaPercentage = slaPercentage;
        this.currentSLAPercentage = currentSLAPercentage;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSlaTime() {
        return slaTime;
    }

    public void setSlaTime(Long slaTime) {
        this.slaTime = slaTime;
    }

    public Float getSlaPercentage() {
        return slaPercentage;
    }

    public void setSlaPercentage(Float slaPercentage) {
        this.slaPercentage = slaPercentage;
    }

    public Float getCurrentSLAPercentage() {
        return currentSLAPercentage;
    }

    public void setCurrentSLAPercentage(Float currentSLAPercentage) {
        this.currentSLAPercentage = currentSLAPercentage;
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
