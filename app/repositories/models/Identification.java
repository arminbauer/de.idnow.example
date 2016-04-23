package repositories.models;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by ebajrami on 4/22/16.
 */
@Entity
public class Identification extends BaseModel {

    /**
     * The unique ID of the identification
     */
    @Id
    private int id;

    /**
     * Name of the user
     */
    private String name;

    /**
     * The time when this identification request was started by the user (Unix format)
     */
    private long time;

    /**
     * The current waiting time of the identification in seconds (since the user started)
     */
    private long waitingTime;

    /**
     * The ID of the company to which this identification belongs
     */
    @ManyToOne
    private Company company;

    @CreatedTimestamp
    private Date dateCreated;

    @UpdatedTimestamp
    private Date dateUpdated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
