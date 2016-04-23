package repositories.models;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by ebajrami on 4/23/16.
 */
@MappedSuperclass
public class BaseModel {

    private Date dateCreated;

    private Date dateUpdated;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
