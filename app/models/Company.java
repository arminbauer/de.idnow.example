package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wolfgang Ostermeier on 09.10.2017.
 *
 * Entity class for Company objects
 */
@Entity
public class Company extends Model {

    @Id
    private int id;

    private String name;

    private Integer slaTime;

    private Float slaPercentage;

    private Float currentSlaPercentage;

    @OneToMany(mappedBy = "company")
    public List<Identification> identifications = new ArrayList<>();

    public static Finder<Integer,Company> find = new Finder<>(Company.class);

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

    public Integer getSlaTime() {
        return slaTime;
    }

    public void setSlaTime(int slaTime) {
        this.slaTime = slaTime;
    }

    public Float getSlaPercentage() {
        return slaPercentage;
    }

    public void setSlaPercentage(float slaPercentage) {
        this.slaPercentage = slaPercentage;
    }

    public Float getCurrentSlaPercentage() {
        return currentSlaPercentage;
    }

    public void setCurrentSlaPercentage(float currentSlaPercentage) {
        this.currentSlaPercentage = currentSlaPercentage;
    }

    public List<Identification> getIdentifications() {
        return identifications;
    }

    public void setIdentifications(List<Identification> identifications) {
        this.identifications = identifications;
    }
}
