package models;

public class Company {

  private int    id;                   // The unique ID of the company
  private String name;                 // The name of the company
  private int    slaTime;              // The SLA (Service Level Agreement) time of this company in seconds
  private float  slaPercentage;        // The SLA (Service Level Agreement) percentage of this company as float
  private float  currentSLAPercentage; // The current SLA percentage of this company in this month

  public Company(int newId, String newName, int newSLATime, float newSLAPercentage) {
    // do some validations
    if (0 > newId) {
      throw new IllegalArgumentException("id may not be less than 0");
    }
    if ((null == newName) ||
        (newName.isEmpty())) {
      throw new IllegalArgumentException("name may not be null or empty");
    }
    if (0 > newSLATime) {
      throw new IllegalArgumentException("slaTime may not be less than 0");
    }
    if ((0.0f > newSLAPercentage) ||
        (1.0f < newSLAPercentage)) {
      throw new IllegalArgumentException("slaPercentage needs to be within [0, 1]");
    }

    // validations passed
    this.id            = newId;
    this.name          = newName;
    this.slaTime       = newSLATime;
    this.slaPercentage = newSLAPercentage;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public int getSLATime() {
    return this.slaTime;
  }

  public float getSLAPercentage() {
    return this.slaPercentage;
  }

  public void setCurrentSLAPercentage(float newCurrentSLAPercentage) {
    if ((0.0f > newCurrentSLAPercentage) ||
        (1.0f < newCurrentSLAPercentage)) {
      throw new IllegalArgumentException("currentSLAPercentage needs to be within [0, 1]");
    }

    this.currentSLAPercentage = newCurrentSLAPercentage;
  }

  public float getCurrentSLAPercentage() {
    return this.currentSLAPercentage;
  }

  public String toString() {
    return "Company inspect: \r\n" +
      "id: " + this.id + "\r\n" +
      "name: \"" + this.name + "\"\r\n" +
      "slaTime: " + this.slaTime + "\r\n" +
      "slaPercentage: " + this.slaPercentage + "\r\n" +
      "currentSLAPercentage: " + this.currentSLAPercentage;
  }
}
