package models;


import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by chetan on 23.01.2018.
 */
@Entity
public class Company extends Model {

    @Id
    private Long id;

    private String companyName;

    private Long slaTime;

    private Float slaPercentage;

    private Float currentSlaPercentage;

    public static Find<Long, Company> companyFind = new Find<Long, Company>() {
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getSlaTime() {
        return slaTime;
    }

    public void setSlaTime(Long slaTime) {
        this.slaTime = slaTime;
    }

    public Float getSlaPercentage() {
        return slaPercentage;
    }

    public void setSlaPercentage(Float slaPercentage) {
        this.slaPercentage = slaPercentage;
    }

    public Float getCurrentSlaPercentage() {
        return currentSlaPercentage;
    }

    public void setCurrentSlaPercentage(Float currentSlaPercentage) {
        this.currentSlaPercentage = currentSlaPercentage;
    }
}
