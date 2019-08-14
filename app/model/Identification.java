package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import play.data.validation.Constraints;

import java.util.Objects;

public class Identification {

    @Constraints.Required
	@JsonProperty("Id")
    private Integer id;

    @Constraints.MaxLength(20)
    @Constraints.MinLength(2)
    @Constraints.Required
	@JsonProperty("Name")
    private String name;

	@JsonProperty("Time")
	private Long time;

    @Constraints.Min(0)
    @Constraints.Required
    @JsonProperty("Waiting_time")
    private Integer waitingTime;

    @Constraints.Required
    @JsonProperty("Companyid")
    private Integer companyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    
    @Override
    public String toString()
    {
        return "Identification: " +
            "id=" + id +
            " name=" + name  +
            " time=" + time +
            " waitingTime=" + waitingTime +
            " companyId=" + companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identification that = (Identification) o;
        return id.equals(that.id) &&
                companyId.equals(that.companyId);
    }

    public boolean validData()
    {
        return id > 0 && !name.isEmpty() && waitingTime >= 0 && companyId >= 0;

    }

}
