package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by sebastian.walter on 01.02.2016.
 */
@Entity
public class Company extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private int slaTime;

    @NotNull
    private float slaPercentage;

    @NotNull
    private float currentSlaPercentage;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSlaTime() {
        return slaTime;
    }

    public void setSlaTime(int slaTime) {
        this.slaTime = slaTime;
    }

    public float getSlaPercentage() {
        return slaPercentage;
    }

    public void setSlaPercentage(float slaPercentage) {
        this.slaPercentage = slaPercentage;
    }

    public float getcurrentSlaPercentage() {
        return currentSlaPercentage;
    }

    public void setCurrentSlaPercentage(float currentSlaPercentage) {
        this.currentSlaPercentage = currentSlaPercentage;
    }
}
