package service.dto;

import org.hibernate.validator.constraints.NotEmpty;
import play.data.validation.Constraints;

/**
 * Created by sreenath on 15.07.16.
 */
public class IdentificationDTO {

    String id;
    @Constraints.Required
    @NotEmpty
    String nameOfUser;
    @Constraints.Required
    @Constraints.Min(0)
    long startTime;
    @Constraints.Required
    @Constraints.Min(0)
    long waitTimeSeconds;
    @Constraints.Required
    @NotEmpty
    String companyId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getWaitTimeSeconds() {
        return waitTimeSeconds;
    }

    public void setWaitTimeSeconds(long waitTimeSeconds) {
        this.waitTimeSeconds = waitTimeSeconds;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
