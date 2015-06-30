package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by sleski on 30.06.2015.
 */
public class Company {

    private long id;
    private String name;
    private int sla_time;
    private float sla_percentage, current_sla_percentage;

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

    public int getSla_time() {
        return sla_time;
    }

    public void setSla_time(int sla_time) {
        this.sla_time = sla_time;
    }

    public float getSla_percentage() {
        return sla_percentage;
    }

    public void setSla_percentage(float sla_percentage) {
        this.sla_percentage = sla_percentage;
    }

    public float getCurrent_sla_percentage() {
        return current_sla_percentage;
    }

    public void setCurrent_sla_percentage(float current_sla_percentage) {
        this.current_sla_percentage = current_sla_percentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        return new EqualsBuilder()
                .append(getId(), company.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .toHashCode();
    }
}
