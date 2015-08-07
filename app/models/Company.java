package models;

/**
 * Created by maria.barbosz on 8/7/2015.
 */
public class Company {

    private String id;
    private String name;
    private int slaTime;
    private double slaPercentage;
    private double currentSlaPercentage;

    public Company() {
    }

    public Company(String id) {
        this.id = id;
    }

    public Company(String id, String name, int slaTime, double slaPercentage, double currentSlaPercentage) {
        this.id = id;
        this.name = name;
        this.slaTime = slaTime;
        this.slaPercentage = slaPercentage;
        this.currentSlaPercentage = currentSlaPercentage;
    }

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

    public int getSlaTime() {
        return slaTime;
    }

    public void setSlaTime(int slaTime) {
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
}
