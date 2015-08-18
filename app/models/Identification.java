package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Identification implements Serializable, Comparable {

    private Long id;

    @JsonProperty("companyid")
    private long companyId;

    private String name;

    private long time;

    @JsonProperty("waiting_time")
    private long waitingTime;

    @JsonIgnore
    private Company company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public int compareTo(Object o) {
        Identification that = (Identification) o;
        if (company == null || that.company == null) {
            throw new IllegalStateException("Company not assigned");
        }
        int companyDiff = company.compareTo(that.company);
        if (companyDiff != 0) {
            return companyDiff;
        }

        // bigger waitingTime first
        return -Long.compare(getWaitingTime(), that.getWaitingTime());
    }
}
