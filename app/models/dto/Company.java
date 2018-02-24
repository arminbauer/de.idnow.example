package models.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Model;



@Entity
public class Company extends Model{
	
	@Id
	public Integer id;
	public String name;
	public int sla_time;
	public float sla_percentage;
	public float current_sla_percentage;
	
	public Company(){
		
	}
	
public Company(Integer id, String name, int sla_time,float sla_percentage,float current_sla_percentage){
		super();
		this.id = id;
		this.name = name;
		this.sla_time = sla_time;
		this.sla_percentage = sla_percentage;
		this.current_sla_percentage = current_sla_percentage;
	}
	
	public static Finder find = new Finder(Integer.class, Company.class);

}
