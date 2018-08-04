package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.Index;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.UnixTimestampDateTimeDeserializer;
import json.UnixTimestampDateTimeSerializer;

import javax.persistence.*;
import java.time.Duration;
import java.time.Instant;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
@Entity
@Table(name = "IDENTIFICATION")
public class Identification extends Model {
  @JsonProperty("Id")
  @Column(nullable = false)
  @Id
  private Long id;
  @JsonProperty("Name")
  @Column(nullable = false)
  private String username;
  @JsonProperty("Time")
  @JsonSerialize(using = UnixTimestampDateTimeSerializer.class)
  @JsonDeserialize(using = UnixTimestampDateTimeDeserializer.class)
  @Column(nullable = false)
  private Instant startedAt;
  @ManyToOne(optional = false)
  @JoinColumn(name = "COMPANY_ID", nullable = false, updatable = false)
  @Column(nullable = false)
  private Company company;
  @Index
  @Column(nullable = false)
  private boolean isPending = true;
  @Index
  @Column(nullable = false)
  private boolean isDeleted = false;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public Instant getStartedAt() {
    return startedAt;
  }

  public void setStartedAt(final Instant startedAt) {
    this.startedAt = startedAt;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(final Company company) {
    this.company = company;
  }

  @Transient
  @JsonSerialize(using = UnixTimestampDateTimeSerializer.class)
  @JsonDeserialize(using = UnixTimestampDateTimeDeserializer.class)
  @JsonGetter("Waiting_time")
  public long waitingTime() {
    return Duration.between(startedAt, Instant.now()).toMillis() / 1000;
  }

  @JsonGetter("Companyid")
  public String companyId() {
    return company.getId();
  }

  public boolean isPending() {
    return isPending;
  }

  public void setPending(final boolean pending) {
    isPending = pending;
  }

  public boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(final boolean deleted) {
    isDeleted = deleted;
  }
}
