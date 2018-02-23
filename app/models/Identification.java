package models;

import javax.validation.constraints.NotNull;
import java.util.UUID;

// No builder pattern for now, for simplicity.
public class Identification {

    // Assumed constraint.
    @NotNull(message = "Id cannot be null.")
    private UUID id;

    // Assumed constraint.
    @NotNull(message = "Name cannot be null.")
    private String name;

    private Long time; // No validation currently.
    private Long waitingTime; // No validation currently.

    // Assumed constraint.
    @NotNull(message = "CompanyId cannot be null.")
    private UUID companyId;

    public Identification() {
    }

    public Identification(UUID id, String name, Long time, Long waitingTime, UUID companyId) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.waitingTime = waitingTime;
        this.companyId = companyId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public Long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Long waitingTime) {
        this.waitingTime = waitingTime;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }
}
