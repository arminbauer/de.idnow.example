package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

@Entity
public class Identification extends Model {

    @Id
    private int id;

    private String name;

    private Long time;

    private Integer waitingTime;

    @ManyToOne
    @JoinColumn(name = "companyid")
    private Company company;

    public static Finder<Integer,Identification> find = new Finder<>(Identification.class);

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

    public Long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Integer getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

}
