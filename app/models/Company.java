package models;

import java.util.*;


public class  Company {

	
	public String id;
	public String name;
	public int sla_time;
	public int sla_percentage;
	public int current_sla_percentage;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSla_time() {
		return sla_time;
	}
	public void setSla_time(int sla_time) {
		this.sla_time = sla_time;
	}
	public int getSla_percentage() {
		return sla_percentage;
	}
	public void setSla_percentage(int sla_percentage) {
		this.sla_percentage = sla_percentage;
	}
	public int getCurrent_sla_percentage() {
		return current_sla_percentage;
	}
	public void setCurrent_sla_percentage(int current_sla_percentage) {
		this.current_sla_percentage = current_sla_percentage;
	}
	
	

}