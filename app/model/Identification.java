package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import repository.CompanyRepository;
import unmarshalling.IdentificationDeserializer;

import java.util.Objects;

@JsonDeserialize(using = IdentificationDeserializer.class)
public class Identification implements Comparable<Identification> {
  @JsonProperty("id")
  private long id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("time")
  private long time;

  @JsonProperty("waiting_time")
  private long waitingTime;

  @JsonProperty("companyid")
  private long companyId;

  public Identification() { // Jackson requirement: default constructor
  }

  public Identification(long id, String name, long time, long waitingTime, long companyId) {
    this.id = id;
    this.name = name;
    this.time = time;
    this.waitingTime = waitingTime;
    this.companyId = companyId;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public long getTime() {
    return time;
  }

  public long getWaitingTime() {
    return waitingTime;
  }

  public long getCompanyId() {
    return companyId;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public void setWaitingTime(long waitingTime) {
    this.waitingTime = waitingTime;
  }

  public void setCompanyId(long companyId) {
    this.companyId = companyId;
  }

  @Override
  public int compareTo(Identification otherIdentification) {
    if (companyId == otherIdentification.companyId) {
      return Long.compare(waitingTime, otherIdentification.waitingTime) * -1;
    }

    Company company = CompanyRepository.getCompany(companyId);
    Company otherCompany = CompanyRepository.getCompany(otherIdentification.companyId);

    int companyComparison = company.compareTo(otherCompany);
    if (companyComparison < 0) {
      return -1;
    }

    if (companyComparison > 0) {
      return 1;
    }

    if (companyComparison == 0) {
      return Long.compare(waitingTime, otherIdentification.waitingTime);
    }

    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Identification) {
      Identification other = (Identification) obj;
      return (id == other.id)
          && Objects.equals(name, other.name)
          && (time == other.time)
          && (waitingTime == other.waitingTime)
          && (companyId == other.companyId);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, time, waitingTime, companyId);
  }
}
