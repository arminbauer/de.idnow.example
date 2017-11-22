package models.company.entities;

public class Company {
    private long id;
    private String name;
    private int slaTime;
    private float slaPercentage;
    private float currentSlaPercentage;

    public Company() {
    }

    public Company(long id, String name, int slaTime, float slaPercentage, float currentSlaPercentage) {
        this.id = id;
        this.name = name;
        this.slaTime = slaTime;
        this.slaPercentage = slaPercentage;
        this.currentSlaPercentage = currentSlaPercentage;
    }

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

    public float getCurrentSlaPercentage() {
        return currentSlaPercentage;
    }

    public void setCurrentSlaPercentage(float currentSlaPercentage) {
        this.currentSlaPercentage = currentSlaPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        return id == company.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
