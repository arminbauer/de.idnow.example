package repositories.models;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import com.fasterxml.jackson.annotation.JsonBackReference;
import play.data.format.Formats;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * A company, which is a customer of IDnow
 * Created by ebajrami on 4/22/16.
 */
@Entity
public class Company extends BaseModel {

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
    private long slaTime;

    /**
     * The current waiting time of the identification in seconds (since the user started)
     */
    private double slaPercentage;

    private double currentSlaPercentage;

    @OneToMany(mappedBy = "company")
    @JsonBackReference
    private List<Identification> identifications;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedTimestamp
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private Date dateCreated = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @UpdatedTimestamp
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = true)
    private Date dateUpdated = new Date();

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

    public long getSlaTime() {
        return slaTime;
    }

    public void setSlaTime(long slaTime) {
        this.slaTime = slaTime;
    }

    public double getSlaPercentage() {
        return slaPercentage;
    }

    public void setSlaPercentage(double slaPercentage) {
        this.slaPercentage = slaPercentage;
    }

    public double getCurrentSlaPercentage() {
        return currentSlaPercentage;
    }

    public void setCurrentSlaPercentage(double currentSlaPercentage) {
        this.currentSlaPercentage = currentSlaPercentage;
    }

    public List<Identification> getIdentifications() {
        return identifications;
    }
}
