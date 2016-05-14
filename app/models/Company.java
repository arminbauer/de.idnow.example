package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import support.ValidFloat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Created by nick on 13.05.16.
 */
public class Company extends AbstractDto<Long> {

    @Size(min = 3, max = 128)
    @NotNull
    @JsonProperty("name")
    private String name;

    @Min(0)
    @JsonProperty("sla_time")
    private Integer slaTimeSeconds;

    @ValidFloat(minValue = 0.0F, maxValue = 1.0F)
    @JsonProperty("sla_percentage")
    private Float slaPercentage;

    @ValidFloat(minValue = 0.0F, maxValue = 1.0F)
    @JsonProperty("current_sla_percentage")
    private Float currentSlaPercentage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSlaTimeSeconds() {
        return slaTimeSeconds;
    }

    public void setSlaTimeSeconds(int slaTimeSeconds) {
        this.slaTimeSeconds = slaTimeSeconds;
    }

    public Float getSlaPercentage() {
        return slaPercentage;
    }

    public void setSlaPercentage(float slaPercentage) {
        this.slaPercentage = slaPercentage;
    }

    public Float getCurrentSlaPercentage() {
        return currentSlaPercentage;
    }

    public void setCurrentSlaPercentage(float currentSlaPercentage) {
        this.currentSlaPercentage = currentSlaPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return Objects.equals(getId(), company.getId()) &&
                Objects.equals(slaTimeSeconds, company.slaTimeSeconds) &&
                Objects.equals(slaPercentage, company.slaPercentage) &&
                Objects.equals(currentSlaPercentage, company.currentSlaPercentage) &&
                Objects.equals(name, company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, slaTimeSeconds, slaPercentage, currentSlaPercentage);
    }
}
