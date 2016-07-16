package service.dto;


import org.hibernate.validator.constraints.NotEmpty;
import play.data.validation.Constraints;

/**
 * Created by sreenath on 15.07.16.
 */
public class CompanyDTO {

    String id;
    @Constraints.Required
    @NotEmpty
    String name;
    @Constraints.Required
    @Constraints.Min(0)
    int slaTimeSeconds;
    @Constraints.Min(0)
    @Constraints.Max(1)
    double slaPercent;
    @Constraints.Min(0)
    @Constraints.Max(1)
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
