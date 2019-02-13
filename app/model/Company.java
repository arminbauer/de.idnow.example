package model;


public class Company {
    private String id;
    private String name;
    private int sla_time;
    private double sla_percentage;
    private double current_sla_percentage;

    public Company() {
    }

    /*
    * Id: The unique ID of the company
    * Name: The name of the company
    * SLA_time: The SLA (Service Level Agreement) time of this company in seconds
    * SLA_percentage: The SLA (Service Level Agreement) percentage of this company as float
    * current_SLA_percentage: The current SLA percentage of this company in this month
    * (e.g. 0.95 would mean that IDnow achieved an SLA_percentage of 95% for this company for this month.
    * If this is lower than SLA_percentage at the end of the month, we would not have reached to agreed SLA)
    * */
    public Company(String id, String name, int sla_time, double sla_percentage, double current_sla_percentage) {
        this.id = id;
        this.name = name;
        this.sla_time = sla_time;
        this.sla_percentage = sla_percentage;
        this.current_sla_percentage = current_sla_percentage;
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

    public int getSla_time() {
        return sla_time;
    }

    public void setSla_time(int sla_time) {
        this.sla_time = sla_time;
    }

    public double getSla_percentage() {
        return sla_percentage;
    }

    public void setSla_percentage(double sla_percentage) {
        this.sla_percentage = sla_percentage;
    }

    public double getCurrent_sla_percentage() {
        return current_sla_percentage;
    }

    public void setCurrent_sla_percentage(double current_sla_percentage) {
        this.current_sla_percentage = current_sla_percentage;
    }
}