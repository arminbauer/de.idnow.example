package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

/**
 * Created by Talal Ahmed on 18/08/2018
 */
public class Identification {

    private int id;
    private String name;
    private long time;

    @JsonProperty("waiting_time")
    private long waitingTime;

    @JsonProperty("companyid")
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

    public static class Builder {
        Identification identification = new Identification();

        public Builder id(int id) {
            identification.id = id;
            return this;
        }

        public Builder name(String name) {
            identification.name = name;
            return this;
        }

        public Builder time(long time) {
            identification.time = time;
            return this;
        }

        public Builder waitingTime(long waitingTime) {
            identification.waitingTime = waitingTime;
            return this;
        }

        public Builder companyId(int companyId) {
            identification.companyId = companyId;
            return this;
        }

        public Identification get() {
            return identification;
        }
    }

    @JsonIgnore
    public static Identification fromJson(JsonNode json) {
        return Json.fromJson(json, Identification.class);
    }

    @JsonIgnore
    public JsonNode toJson() {
        return Json.toJson(this);
    }

    @Override
    public boolean equals(Object that) {
        if (that == null) return false;

        if (this == that) return true;

        if (!(that instanceof Identification)) return false;

        Identification thatIdent = (Identification) that;

        return this.id == thatIdent.id;
    }

    @Override
    public String toString() {
        return "Identification{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", waitingTime=" + waitingTime +
                ", companyId=" + companyId +
                '}';
    }
}
