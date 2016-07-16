package service.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import play.data.validation.Constraints;

/**
 * DTO object for Company.
 * Direct representation of the JSON for interaction with user.
 *
 * Created by sreenath on 15.07.16.
 */
public class CompanyDTO {

    String id;
    @Constraints.Required
    @NotEmpty
    @JsonProperty("Name")
    String name;
    @Constraints.Required
    @Constraints.Min(0)
    @JsonProperty("SLA_time")
    int slaTimeSeconds;
    @Constraints.Min(0)
    @Constraints.Max(1)
    @JsonProperty("SLA_percentage")
    double slaPercent;
    @Constraints.Min(0)
    @Constraints.Max(1)
    @JsonProperty("Current_SLA_percentage")
    double currentSlaPercent;

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

    public int getSlaTimeSeconds() {
        return slaTimeSeconds;
    }

    public void setSlaTimeSeconds(int slaTimeSeconds) {
        this.slaTimeSeconds = slaTimeSeconds;
    }

    public double getSlaPercent() {
        return slaPercent;
    }

    public void setSlaPercent(double slaPercent) {
        this.slaPercent = slaPercent;
    }

    public double getCurrentSlaPercent() {
        return currentSlaPercent;
    }

    public void setCurrentSlaPercent(double currentSlaPercent) {
        this.currentSlaPercent = currentSlaPercent;
    }
}
