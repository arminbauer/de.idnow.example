package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Wolfgang Ostermeier on 09.10.2017.
 *
 * IndentificationTO class that gets parsed from JSON
 */
public class IdentificationTO {

    private int id;

    private String name;

    private long time;

    @JsonProperty("waiting_time")
    private int waitingTime;

    private int companyid;

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

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }
}
