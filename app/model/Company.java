package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Company
{
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("sla_time")
    private Integer slaTime;

    @JsonProperty("sla_percentage")
    private Double slaPercentage;

    @JsonProperty("current_sla_percentage")
    private Double currentSlaPercentage;

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

    public Integer getSlaTime()
    {
        return slaTime;
    }

    public void setSlaTime(Integer slaTime)
    {
        this.slaTime = slaTime;
    }

    public Double getSlaPercentage()
    {
        return slaPercentage;
    }

    public void setSlaPercentage(Double slaPercentage)
    {
        this.slaPercentage = slaPercentage;
    }

    public Double getCurrentSlaPercentage()
    {
        return currentSlaPercentage;
    }

    public void setCurrentSlaPercentage(Double currentSlaPercentage)
    {
        this.currentSlaPercentage = currentSlaPercentage;
    }

    @Override
    public String toString()
    {
        return "Company{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", slaTime=" + slaTime +
            ", slaPercentage=" + slaPercentage +
            ", currentSlaPercentage=" + currentSlaPercentage +
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

        Company company = (Company) o;

        if (id != null ? !id.equals(company.id) : company.id != null)
        {
            return false;
        }
        if (name != null ? !name.equals(company.name) : company.name != null)
        {
            return false;
        }
        if (slaTime != null ? !slaTime.equals(company.slaTime) : company.slaTime != null)
        {
            return false;
        }
        if (slaPercentage != null ? !slaPercentage.equals(company.slaPercentage) : company.slaPercentage != null)
        {
            return false;
        }
        return currentSlaPercentage != null ? currentSlaPercentage.equals(company.currentSlaPercentage) : company.currentSlaPercentage == null;
    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (slaTime != null ? slaTime.hashCode() : 0);
        result = 31 * result + (slaPercentage != null ? slaPercentage.hashCode() : 0);
        result = 31 * result + (currentSlaPercentage != null ? currentSlaPercentage.hashCode() : 0);
        return result;
    }
}
