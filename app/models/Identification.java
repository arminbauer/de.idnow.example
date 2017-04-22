package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Identification model class.
 */

@Entity
public final class Identification extends Model implements Comparable<Identification> {
    /**
     * The unique ID of the identification.
     */
    @Id
    private String id;

    /**
     * Name of the user.
     */
    private String name;
    /**
     * The time when this identification request was started by the user (Unix format)
     * Default: current time.
     */
    private String time;
    /**
     * The current waiting time of the identification in seconds (since the user started)
     */
    private float waitingTime;
    /**
     * The ID of the company to which this identification belongs
     */
    private String companyId;

    //@OneToMany
    private Company company;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public float getWaitingTime() {
        return waitingTime;
    }

    public String getCompanyId() {
        return companyId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setWaitingTime(float waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public int compareTo(Identification o) {
        if (o.getCompany().equals(this.getCompany())) {
            return o.getWaitingTime() > this.getWaitingTime() ? 1 : -1;
        } else {
            return compareDifferentCompanies(o);
        }
    }


    /**
     * Compares identifications of different companies.
     *
     * @param o
     * @return
     */
    private int compareDifferentCompanies(Identification o) {
        Company comp1 = o.getCompany();
        Company comp2 = this.getCompany();

        if (comp1.getSLATime() == comp2.getSLATime()) {
            if (comp1.getSLAPercentage() == comp2.getSLAPercentage()) {
                return comp1.getCurrentSLAPercentage() < comp2.getCurrentSLAPercentage() ? 1 : -1;
            } else {
                return comp1.getSLAPercentage() < comp2.getSLAPercentage() ? 1 : -1;
            }
        } else {
            if (comp1.hasReachedSlaPercentage()) {
                return 1;
            } else if (comp2.hasReachedSlaPercentage()) {
                return -1;
            } else {
                return comp1.getSLATime() < comp2.getSLATime() ? 1 : -1;
            }
        }
    }
}