package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bduisenov on 18/02/16.
 */
public final class Identification {

    private String id;

    private String name;

    private long time;

    @JsonProperty("waiting_time")
    private Integer waitingTime;

    @JsonProperty("companyid")
    private String companyId;

    public Identification() {
    }

    public Identification(String companyId, String id, String name, long time, Integer waitingTime) {
        this.companyId = companyId;
        this.id = id;
        this.name = name;
        this.time = time;
        this.waitingTime = waitingTime;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    public Integer getWaitingTime() {
        return waitingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Identification that = (Identification) o;

        if (time != that.time)
            return false;
        if (!id.equals(that.id))
            return false;
        if (!name.equals(that.name))
            return false;
        if (!waitingTime.equals(that.waitingTime))
            return false;
        return companyId.equals(that.companyId);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + waitingTime.hashCode();
        result = 31 * result + companyId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Identification{" +
                "companyId='" + companyId + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", waitingTime=" + waitingTime +
                '}';
    }
}
