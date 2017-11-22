package models.identification.jsons;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentificationJson {
    public long id;

    public String name;

    public long time;

    @JsonProperty("waiting_time")
    public int waitingTime;

    @JsonProperty("companyid")
    public long companyId;

    public IdentificationJson() {
    }

    public IdentificationJson(long id, String name, long time, int waitingTime, long companyId) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.waitingTime = waitingTime;
        this.companyId = companyId;
    }
}
