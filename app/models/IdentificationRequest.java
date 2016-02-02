package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by sebastian.walter on 02.02.2016.
 */
public class IdentificationRequest {

    private String name;

    private int time;

    @JsonProperty("waiting_time")
    private int waitingTime;

    @JsonProperty("companyid")
    private int companyId;

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getCompanyId() {
        return companyId;
    }
}
