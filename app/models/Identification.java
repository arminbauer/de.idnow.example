package models;

public class Identification {

  private int    id;                   // The unique ID of the identification
  private String name;                 // Name of the user
  private int    startTime;            // The time when this identification request was started by the user (Unix format)
  private int    waitingTime;          // The current waiting time of the identification in seconds (since the user started)
  private int    companyId;            // The ID of the company to which this identification belongs

  public Identification(int newId, String newName, int newStartTime, int newWaitingTime, int newCompanyId) {
    // do some validations
    if (0 > newId) {
      throw new IllegalArgumentException("id may not be less than 0");
    }
    if ((null == newName) ||
        (newName.isEmpty())) {
      throw new IllegalArgumentException("name may not be null or empty");
    }
    if (0 > newStartTime) {
      throw new IllegalArgumentException("startTime may not be less than 0");
    }
    long unixTime = System.currentTimeMillis() / 1000L;
    if (unixTime < newStartTime) {
      throw new IllegalArgumentException("startTime may not be in the future");
    }
    if (0 > newWaitingTime) {
      throw new IllegalArgumentException("waitingTime may not be less than 0");
    }
    if (unixTime < newStartTime + newWaitingTime) {
      throw new IllegalArgumentException("identification needs to have ended in the past");
    }
    if (0 > newCompanyId) {
      throw new IllegalArgumentException("companyId may not be less than 0");
    }

    this.id          = newId;
    this.name        = newName;
    this.startTime   = newStartTime;
    this.waitingTime = newWaitingTime;
    this.companyId   = newCompanyId;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public int getStartTime() {
    return this.startTime;
  }

  public int getWaitingTime() {
    return this.waitingTime;
  }

  public int getCompanyId() {
    return this.companyId;
  }

}
