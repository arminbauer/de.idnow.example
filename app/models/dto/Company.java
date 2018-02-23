package models.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Model;



@Entity
public class Company extends Model{
	@Id
	public int id;
	public String name;
	public int sla_time;
	public float sla_percentage;
	public float current_sla_percentage;
	public static Finder find = new Finder(Integer.class, Company.class);

}
