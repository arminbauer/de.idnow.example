package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Identification implements Scorable {
    private int id;
    private String name;
    private long time;
    private long waiting_time;
    private int companyid;
    private Company company;

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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getWaiting_time() {
        return waiting_time;
    }

    public void setWaiting_time(long waiting_time) {
        this.waiting_time = waiting_time;
    }

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    @JsonIgnore
    public Company getCompany() {
        return company;
    }

    @JsonIgnore
    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Identification{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", waiting_time=" + waiting_time +
                ", companyid=" + companyid +
                '}';
    }

    @Override
    public double score() {
        try {
            if (this.getCompany() == null) {
                return 0;
            }
            double score1 = Scorable.weight1 * ((double) this.getCompany().getSla_time() / this.getWaiting_time());
            double score2 = Scorable.weight2 * (this.getCompany().getCurrent_sla_percentage() / this.getCompany().getSla_percentage());

            if (score1 == Double.POSITIVE_INFINITY || score2 == Double.POSITIVE_INFINITY) {
                throw new ArithmeticException("Possible divided by zero");
            }

            return score1 + score2;
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
}
