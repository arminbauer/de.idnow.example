package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import play.data.validation.Constraints;

public class Company {

    @Constraints.Required
	@JsonProperty("Id")
    private Integer id;

    @Constraints.MaxLength(20)
    @Constraints.MinLength(2)
    @Constraints.Required
    @JsonProperty("Name")
    private String name;

    @Constraints.Min(1)
    @Constraints.Required
    @JsonProperty("SLA_time")
    private Integer slaTime;

    @Constraints.Max(1)
    @Constraints.Min(0)
    @Constraints.Required
    @JsonProperty("SLA_percentage")
    private Float slaPercentage;

    @Constraints.Max(1)
    @Constraints.Min(0)
    @Constraints.Required
    @JsonProperty("Current_SLA_percentage")
    private Float currentSlaPercentage;

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

    public Float getSlaPercentage() {
        return slaPercentage;
    }

    public void setSlaPercentage(Float slaPercentage) {
        this.slaPercentage = slaPercentage;
    }

    public Float getCurrentSlaPercentage() {
        return currentSlaPercentage;
    }

    public void setCurrentSlaPercentage(Float currentSlaPercentage) {
        this.currentSlaPercentage = currentSlaPercentage;
    }
    
    @Override
    public String toString()
    {
        return "Company : " +
            "id=" + id +
            " name=" + name +
            " slaTime=" + slaTime +
            " slaPercentage=" + slaPercentage +
            " currentSlaPercentage=" + currentSlaPercentage;
    }

    public boolean validData()
    {
        return (id >= 0 && !name.isEmpty() && slaTime >= 0 && slaPercentage >= 0 && slaPercentage <= 1
                && currentSlaPercentage >= 0 && currentSlaPercentage <= 1);

    }
}
