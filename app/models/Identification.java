package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * The Identification model.
 */
public class Identification {

    @NotNull
    @JsonProperty("id")
    private Integer id;

    @NotNull
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("time")
    private long time;

    @NotNull
    @JsonProperty("waiting_time")
    private float waitingTime;

    @NotNull
    @JsonProperty("companyid")
    private Integer companyId;

    @JsonIgnore
    private Company company;

    /**
     * Instantiates a new Identification.
     */
    public Identification() {
    }

    /**
     * Instantiates a new Identification.
     *
     * @param id          the id
     * @param name        the name
     * @param time        the time
     * @param waitingTime the waiting time
     * @param companyId   the company id
     */
    public Identification(Integer id, String name, long time, float waitingTime, Integer companyId) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.waitingTime = waitingTime;
        this.companyId = companyId;
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
     * Gets time.
     *
     * @return the time
     */
    public long getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * Gets waiting time.
     *
     * @return the waiting time
     */
    public float getWaitingTime() {
        return waitingTime;
    }

    /**
     * Sets waiting time.
     *
     * @param waitingTime the waiting time
     */
    public void setWaitingTime(float waitingTime) {
        this.waitingTime = waitingTime;
    }

    /**
     * Gets company id.
     *
     * @return the company id
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * Sets company id.
     *
     * @param companyId the company id
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * Gets company.
     *
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Sets company.
     *
     * @param company the company
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * calculates waiting time.
     *
     * @return the calculated value
     */
    public float calculateWaitingTime() {
        return this.getCompany().getSlaTime() - this.getWaitingTime();
    }
}
