package models;

import java.util.Date;

/**
 * Created by maria.barbosz on 8/7/2015.
 */
public class Identification {

    private String id;
    private String name;
    private Date time;
    private long waitingTime;
    private String companyId;

    public Identification() {
    }

    public Identification(String id) {
        this.id = id;
    }

    public Identification(String id, String name, Date time, long waitingTime, String companyId) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.waitingTime = waitingTime;
        this.companyId = companyId;
    }

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
