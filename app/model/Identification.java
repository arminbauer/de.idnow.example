package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Identification
{
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("time")
    private Long time;

    @JsonProperty("waiting_time")
    private Integer waitingTime;

    @JsonProperty("companyid")
    private Integer companyId;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Long getTime()
    {
        return time;
    }

    public void setTime(Long time)
    {
        this.time = time;
    }

    public Integer getWaitingTime()
    {
        return waitingTime;
    }

    public void setWaitingTime(Integer waitingTime)
    {
        this.waitingTime = waitingTime;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(Integer companyId)
    {
        this.companyId = companyId;
    }

    @Override
    public String toString()
    {
        return "Identification{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", time=" + time +
            ", waitingTime=" + waitingTime +
            ", companyId=" + companyId +
            '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Identification that = (Identification) o;

        if (id != null ? !id.equals(that.id) : that.id != null)
        {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null)
        {
            return false;
        }
        if (time != null ? !time.equals(that.time) : that.time != null)
        {
            return false;
        }
        if (waitingTime != null ? !waitingTime.equals(that.waitingTime) : that.waitingTime != null)
        {
            return false;
        }
        return companyId != null ? companyId.equals(that.companyId) : that.companyId == null;
    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (waitingTime != null ? waitingTime.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }
}
