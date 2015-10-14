package models;

import javax.persistence.*;

@Entity
public class Company
{
  @Id
  public int id;

  public String name;
  public int sla_time;
  public float sla_percentage;
  public float current_sla_percentage;
  
}
