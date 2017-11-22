package models.company.jsons;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyJson {
    public  long id;
    public String name;

    @JsonProperty("sla_time")
    public int slaTime;

    @JsonProperty("sla_percentage")
    public float slaPercentage;

    @JsonProperty("current_sla_percentage")
    public float currentSlaPercentage;
}
