package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.Index;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.UnixTimestampDateTimeDeserializer;
import json.UnixTimestampDateTimeSerializer;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
@SuppressWarnings("unused")
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
  private LocalDateTime startedAt;
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
  @Transient
  private Long companyId;

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

  public LocalDateTime getStartedAt() {
    return startedAt;
  }

  public void setStartedAt(final LocalDateTime startedAt) {
    this.startedAt = startedAt;
  }

  @JsonIgnore
  public Company getCompany() {
    return company;
  }

  @JsonIgnore
  public void setCompany(final Company company) {
    this.company = company;
  }

  @Transient
  @JsonGetter("Waiting_time")
  @JsonIgnore
  public long waitingTime() {
    return Duration.between(startedAt, LocalDateTime.now()).toMillis() / 1000;
  }

  @Transient
  @JsonGetter("Companyid")
  public Long getCompanyId() {
    if (company == null) {
      return companyId;
    } else {
      return company.getId();
    }
  }

  @Transient
  @JsonSetter("Companyid")
  public void setCompanyId(final Long companyId) {
    this.companyId = companyId;
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
