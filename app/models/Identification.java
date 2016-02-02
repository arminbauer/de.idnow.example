package models;


/**
 * Created by sebastian.walter on 01.02.2016.
 */
public class Identification implements Comparable<Identification> {

    private long id;

    private Company company;

    private String name;

    private long time;

    private int waitingTime;

    private double priority;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Identification other) {
        return ((Double) other.getPriority()).compareTo((Double) priority);
    }
}
