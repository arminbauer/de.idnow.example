package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Model class for Identification
 *
 * Created by sreenath on 15.07.16.
 */
@Entity
public class Identification extends Model {

    @Id
    String id;
    String nameOfUser;
    long startTime;
    long waitingTime;
    @ManyToOne
    Company Company;

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

    public Company getCompany() {
        return Company;
    }

    public void setCompany(Company company) {
        this.Company = company;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(long waitingTime) {
        this.waitingTime = waitingTime;
    }
}
