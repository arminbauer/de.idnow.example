package models;

import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Manuel Poppe.
 */

public class Company {

    private String id;
    private String name;
    private Long sla_time;
    private Float sla_percentage;
    private Float current_sla_percentage;
    /**
     * <p>reflects the urgency based on the SLA_percentage coverage for this company.
     * It is calculated with every update of one of the corresponding values.
     * -> we only have to do the calculation once upon an update (division is expensive!).
     * This value has a range of [-1;+1], where +1 reflects the highest urgency.</p>
     * <br>current_sla_percentage = 0.0            -> urgency = +1
     * <br>current_sla_percentage = sla_percentage -> urgency = 0
     * <br>current_sla_percentage = 1.0            -> urgency = -1
     */
    @XmlTransient
    private Float urgency;

    public Float getUrgency() {
        return urgency;
    }

    public void setUrgency(Float urgency) {
        this.urgency = urgency;
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

    public Long getSla_time() {
        return sla_time;
    }

    public void setSla_time(Long sla_time) {

        this.sla_time = sla_time;
    }

    public Float getSla_percentage() {
        return sla_percentage;
    }

    public void setSla_percentage(Float sla_percentage) {
        this.sla_percentage = sla_percentage;
        if (current_sla_percentage != null) {
            setUrgency( (current_sla_percentage - sla_percentage) >= 0f ?
                    (current_sla_percentage - sla_percentage) / (1f - sla_percentage) * -1 :
                    (current_sla_percentage - sla_percentage) /  sla_percentage * -1) ;
        }
    }

    public Float getCurrent_sla_percentage() {
        return current_sla_percentage;
    }

    public void setCurrent_sla_percentage(Float current_sla_percentage) {
        this.current_sla_percentage = current_sla_percentage;
        if (sla_percentage != null) {
            setUrgency( (current_sla_percentage - sla_percentage) >= 0f ?
                    (current_sla_percentage - sla_percentage) / (1f - sla_percentage) * -1:
                    (current_sla_percentage - sla_percentage) /  sla_percentage * -1);
        }
    }

    @Override
    public String toString() {
        return "Company{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sla_time=" + sla_time +
                ", sla_percentage=" + sla_percentage +
                ", current_sla_percentage=" + current_sla_percentage +
                ", urgency=" + urgency +
                '}';
    }
}