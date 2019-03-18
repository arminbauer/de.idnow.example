package models;



import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;


/**
 * The Company model.
 */
public class Company {

    @NotNull
    @JsonProperty("id")
    private Integer id;

    @NotNull
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("sla_time")
    private long slaTime;

    @NotNull
    @JsonProperty("sla_percentage")
    private float slaPercentage;

    @NotNull
    @JsonProperty("current_sla_percentage")
    private float currentSlaPercentage;

    /**
     * Instantiates a new Company.
     */
    public Company() {

    }

    /**
     * Instantiates a new Company.
     *
     * @param id                   the id
     * @param name                 the name
     * @param slaTime              the sla time
     * @param slaPercentage        the sla percentage
     * @param currentSlaPercentage the current sla percentage
     */
    public Company(Integer id, String name, long slaTime, float slaPercentage, float currentSlaPercentage) {
        this.id = id;
        this.name = name;
        this.slaTime = slaTime;
        this.slaPercentage = slaPercentage;
        this.currentSlaPercentage = currentSlaPercentage;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets sla time.
     *
     * @return the sla time
     */
    public long getSlaTime() {
        return slaTime;
    }

    /**
     * Sets sla time.
     *
     * @param slaTime the sla time
     */
    public void setSlaTime(long slaTime) {
        this.slaTime = slaTime;
    }

    /**
     * Gets sla percentage.
     *
     * @return the sla percentage
     */
    public float getSlaPercentage() {
        return slaPercentage;
    }

    /**
     * Sets sla percentage.
     *
     * @param slaPercentage the sla percentage
     */
    public void setSlaPercentage(float slaPercentage) {
        this.slaPercentage = slaPercentage;
    }

    /**
     * Gets current sla percentage.
     *
     * @return the current sla percentage
     */
    public float getCurrentSlaPercentage() {
        return currentSlaPercentage;
    }

    /**
     * Sets current sla percentage.
     *
     * @param currentSlaPercentage the current sla percentage
     */
    public void setCurrentSlaPercentage(float currentSlaPercentage) {
        this.currentSlaPercentage = currentSlaPercentage;
    }

    /**
     * SLA % * SAL time
     *
     * @return urgency value
     */
    public float urgencyValue() {
        return this.slaPercentage * this.getSlaTime();
    }

}
