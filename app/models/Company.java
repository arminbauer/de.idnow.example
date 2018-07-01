package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company extends Model {

    @Id
    public Long id;

    @Column(unique = true, nullable = false)
    public String name;

    @Column(nullable = false)
    @JsonProperty("sla_time")
    public Integer slaTime;

    @Column(nullable = false)
    @JsonProperty("sla_percentage")
    public Float slaPercentage;

    @Column(nullable = false)
    @JsonProperty("current_sla_percentage")
    public Float currentSlaPercentage;

    public Company(String name, Integer slaTime, Float slaPercentage, Float currentSlaPercentage) {
        this.name = name;
        this.slaTime = slaTime;
        this.slaPercentage = slaPercentage;
        this.currentSlaPercentage = currentSlaPercentage;
    }
}
