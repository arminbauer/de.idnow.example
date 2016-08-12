package entities;

import com.fasterxml.jackson.annotation.*;
import controllers.CompanyIdResolver;

import java.time.Instant;
import java.util.Objects;

/**
 * Created by vld on 9/08/16.
 */
public class Identification {
    private String id;
    private String name;
    private int time;
    @JsonProperty("waiting_time")
    private int waitingTime;
    @JsonIdentityInfo(resolver = CompanyIdResolver.class, property="id", generator = ObjectIdGenerators.PropertyGenerator.class)
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty("companyid")
    private Company company;

    public Identification() {
    }

    public Identification(String id, String name, int time, int waitingTime, Company company) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.waitingTime = waitingTime;
        this.company = company;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public Company getCompany() {
        return company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Identification)) return false;
        Identification that = (Identification) o;
        return waitingTime == that.waitingTime &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(time, that.time) &&
                Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, time, waitingTime, company);
    }
}
