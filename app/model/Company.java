package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import unmarshalling.CompanyDeserializer;

import java.util.Objects;

@JsonDeserialize(using = CompanyDeserializer.class)
public class Company implements Comparable<Company> {
  @JsonProperty("id")
  private long id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("sla_time")
  private long slaTime;

  @JsonProperty("sla_percentage")
  private float slaPercentage;

  @JsonProperty("current_sla_percentage")
  private float currentSlaPercentage;

  public Company() { //  Jackson requirement: default constructor
  }

  public Company(
      long id, String name, long slaTime, float slaPercentage, float currentSlaPercentage) {
    this.id = id;
    this.name = name;
    this.slaTime = slaTime;
    this.slaPercentage = slaPercentage;
    this.currentSlaPercentage = currentSlaPercentage;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public long getSlaTime() {
    return slaTime;
  }

  public float getSlaPercentage() {
    return slaPercentage;
  }

  public float getCurrentSlaPercentage() {
    return currentSlaPercentage;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSlaTime(long slaTime) {
    this.slaTime = slaTime;
  }

  public void setSlaPercentage(float slaPercentage) {
    this.slaPercentage = slaPercentage;
  }

  public void setCurrentSlaPercentage(float currentSlaPercentage) {
    this.currentSlaPercentage = currentSlaPercentage;
  }

  @Override
  public int compareTo(Company other) {
    if (slaTime == other.slaTime && slaPercentage == other.slaPercentage) {
      return Float.compare(currentSlaPercentage, other.currentSlaPercentage);
    }

    if (Float.compare(currentSlaPercentage, slaPercentage) >= 0
        && Float.compare(other.currentSlaPercentage, other.slaPercentage) >= 0) {
      return Long.compare(slaTime, other.slaTime);
    }

    if (Float.compare(currentSlaPercentage, slaPercentage) < 0
        && Float.compare(other.currentSlaPercentage, other.slaPercentage) < 0) {
      return Long.compare(slaTime, other.slaTime);
    }

    if (Float.compare(currentSlaPercentage, slaPercentage) < 0
        && Float.compare(other.currentSlaPercentage, other.slaPercentage) >= 0) {
      return -1;
    }

    if (Float.compare(currentSlaPercentage, slaPercentage) >= 0
        && Float.compare(other.currentSlaPercentage, other.slaPercentage) < 0) {
      return 1;
    }

    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Company) {
      Company other = (Company) obj;

      return (id == other.id)
          && Objects.equals(name, other.name)
          && (slaTime == other.slaTime)
          && (slaPercentage == other.slaPercentage)
          && (currentSlaPercentage == other.currentSlaPercentage);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, slaTime, slaPercentage, currentSlaPercentage);
  }
}
