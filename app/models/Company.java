package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.Index;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
@SuppressWarnings("DefaultAnnotationParam")
@Entity
@Table(name = "COMPANY")
public class Company extends Model {
  @JsonProperty("Id")
  @Id
  @Column(nullable = false, length = 255)
  private String id;
  @JsonProperty("Name")
  @Column(nullable = false, length = 255)
  private String name;
  @JsonProperty("SLA_time")
  @Column(nullable = false)
  private int slaTimeInSeconds;
  @JsonProperty("SLA_percentage")
  @Column(nullable = false)
  private float slaPercentage;
  @JsonProperty("Current_SLA_percentage")
  @Column(nullable = false)
  private float currentSlaPercentage;
  @Index
  @Column
  private boolean isDeleted;

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public int getSlaTimeInSeconds() {
    return slaTimeInSeconds;
  }

  public void setSlaTimeInSeconds(final int slaTimeInSeconds) {
    this.slaTimeInSeconds = slaTimeInSeconds;
  }

  public float getSlaPercentage() {
    return slaPercentage;
  }

  public void setSlaPercentage(final float slaPercentage) {
    this.slaPercentage = slaPercentage;
  }

  public float getCurrentSlaPercentage() {
    return currentSlaPercentage;
  }

  public void setCurrentSlaPercentage(final float currentSlaPercentage) {
    this.currentSlaPercentage = currentSlaPercentage;
  }

  public boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(final boolean deleted) {
    isDeleted = deleted;
  }
}
