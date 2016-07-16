package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Model class for Company
 *
 * Created by sreenath on 15.07.16.
 */
@Entity
public class Company extends Model {

    @Id
    String id;
    String name;
    int slaTimeSeconds;
    double slaPercent;
    double currentSlaPercent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSlaTimeSeconds() {
        return slaTimeSeconds;
    }

    public void setSlaTimeSeconds(int slaTimeSeconds) {
        this.slaTimeSeconds = slaTimeSeconds;
    }

    public double getSlaPercent() {
        return slaPercent;
    }

    public void setSlaPercent(double slaPercent) {
        this.slaPercent = slaPercent;
    }

    public double getCurrentSlaPercent() {
        return currentSlaPercent;
    }

    public void setCurrentSlaPercent(double currentSlaPercent) {
        this.currentSlaPercent = currentSlaPercent;
    }
}
