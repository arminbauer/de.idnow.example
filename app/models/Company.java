package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Company model class.
 */
@Entity
public final class Company extends Model{

    /**
     * The unique ID of the company.
     */
    @Id
    private String id;

    /**
     * Company name.
     */
    private String name;

    /**
     * The SLA (Service Level Agreement) time of this company in seconds.
     */
    private int SLATime;

    /**
     * The SLA (Service Level Agreement) percentage of this company as float.
     */
    private float SLAPercentage;

    /**
     * The current SLA percentage of this company in this month (e.g. 0.95 would mean that IDnow achieved an SLA_percentage of 95% for this company for this month.
     * If this is lower than SLA_percentage at the end of the month, we would not have reached to agreed SLA.
     */
    private float currentSLAPercentage;

    //@OneToMany(mappedBy = "company")
    //private List<Identification> identifications;

//    public List<Identification> getIdentifications() {
//        return identifications;
//    }
//
//    public void setIdentifications(List<Identification> identifications) {
//        this.identifications = identifications;
//    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSLATime() {
        return SLATime;
    }

    public float getSLAPercentage() {
        return SLAPercentage;
    }

    public float getCurrentSLAPercentage() {
        return currentSLAPercentage;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSLATime(int SLATime) {
        this.SLATime = SLATime;
    }

    public void setSLAPercentage(float SLAPercentage) {
        this.SLAPercentage = SLAPercentage;
    }

    public void setCurrentSLAPercentage(float currentSLAPercentage) {
        this.currentSLAPercentage = currentSLAPercentage;
    }

    public boolean hasReachedSlaPercentage(){
        return getCurrentSLAPercentage() <= getSLAPercentage();
    }

    /**
     * This are considered equals companies in terms of SLA.
     *
     * @param comp company to compare with
     * @return true if they are the same
     */
    public boolean equals(Company comp) {
        return (this.getId().equalsIgnoreCase(comp.getId())) ||
                (       this.getCurrentSLAPercentage() == comp.getCurrentSLAPercentage() &&
                        this.getSLAPercentage() == comp.getSLAPercentage() &&
                        this.getSLATime() == this.getSLATime()
                );
    }
}