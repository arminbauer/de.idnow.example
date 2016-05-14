package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;


public class Identification extends AbstractDto<Long> {

    @NotNull
    @Size(min = 3, max = 128)
    @JsonProperty("name")
    private String name;

    @Min(1L)
    @JsonProperty("time")
    private Long createdTs; //todo Perhaps should be stored as Date object.

    @Min(0)
    @JsonProperty("waiting_time")
    private Integer waitTimeSeconds;

    @Min(1L)
    @JsonProperty("companyid")
    private Long companyId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(Long createdTs) {
        this.createdTs = createdTs;
    }

    public Integer getWaitTimeSeconds() {
        return waitTimeSeconds;
    }

    public void setWaitTimeSeconds(int waitTimeSeconds) {
        this.waitTimeSeconds = waitTimeSeconds;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identification that = (Identification) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(waitTimeSeconds, that.waitTimeSeconds) &&
                Objects.equals(name, that.name) &&
                Objects.equals(createdTs, that.createdTs) &&
                Objects.equals(companyId, that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, createdTs, waitTimeSeconds, companyId);
    }
}
