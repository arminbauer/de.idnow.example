package services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ebajrami on 4/23/16.
 */
public class IdentificationDTO {
    /**
     * The unique ID of the identification
     */
    private int id;

    /**
     * Name of the user
     */
    private String name;

    /**
     * The time when this identification request was started by the user (Unix format)
     */
    private long time;

    /**
     * The current waiting time of the identification in seconds (since the user started)
     */
    @JsonProperty("waiting_time")
    private long waitingTime;

    /**
     * The ID of the company to which this identification belongs
     */
    private int companyId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
