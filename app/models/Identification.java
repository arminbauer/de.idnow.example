package models;

import javax.persistence.*;

@Entity
public class Identification
{
  @Id
  public int id;

  public String name;
  public String time;
  public int waiting_time;
  public int companyid;

}
