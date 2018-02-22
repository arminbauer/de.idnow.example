package models;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class Identification {

    // Assumed constraint.
    @NotNull(message = "Id cannot be null.")
    private UUID id;

    // Assumed constraint.
    @NotNull(message = "Name cannot be null.")
    private String name;

    private long time; // No validation currently.
    private long waitingTime; // No validation currently.

    // Assumed constraint.
    @NotNull(message = "CompanyId cannot be null.")
    private UUID companyId;

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

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }
}
