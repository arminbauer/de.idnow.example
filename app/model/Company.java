package model;

public class Company {
    private int id;
    private String name;
    private long sla_time;
    private double sla_percentage;
    private double current_sla_percentage;

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

    public long getSla_time() {
        return sla_time;
    }

    public void setSla_time(long sla_time) {
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

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sla_time=" + sla_time +
                ", sla_percentage=" + sla_percentage +
                ", current_sla_percentage=" + current_sla_percentage +
                '}';
    }
}
