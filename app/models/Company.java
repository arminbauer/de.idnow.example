package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

/**
 * Created by ebajrami on 4/22/16.
 */
@Entity
public class Company extends Model {

    @Id
    private int id;

    private String name;

    private long slaTime;

    private double slaPercentage;

    private long currentSlaPercentage;

    @OneToMany(mappedBy = "company")
    private List<Identification> identifications;

    private Date dateCreated;

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

    public long getCurrentSlaPercentage() {
        return currentSlaPercentage;
    }

    public void setCurrentSlaPercentage(long currentSlaPercentage) {
        this.currentSlaPercentage = currentSlaPercentage;
    }

    public List<Identification> getIdentifications() {
        return identifications;
    }
}
