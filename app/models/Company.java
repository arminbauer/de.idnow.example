package models;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class Company {

    // Assumed constraint.
    @NotNull(message = "Id cannot be null.")
    private UUID id;

    // Assumed constraint.
    @NotNull(message = "Name cannot be null.")
    private String name;

    private long SLATime; // No validation currently.
    private float SLAPercentage; // No validation currently.
    private float currentSLAPercentage; // No validation currently.

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

    public long getSLATime() {
        return SLATime;
    }

    public void setSLATime(long SLATime) {
        this.SLATime = SLATime;
    }

    public float getSLAPercentage() {
        return SLAPercentage;
    }

    public void setSLAPercentage(float SLAPercentage) {
        this.SLAPercentage = SLAPercentage;
    }

    public float getCurrentSLAPercentage() {
        return currentSLAPercentage;
    }

    public void setCurrentSLAPercentage(float currentSLAPercentage) {
        this.currentSLAPercentage = currentSLAPercentage;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", SLATime=" + SLATime +
                ", SLAPercentage=" + SLAPercentage +
                ", currentSLAPercentage=" + currentSLAPercentage +
                '}';
    }
}
