package models.dto;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
public class Identification extends Model implements Comparable{
	@Id
	public Integer id;
	public String name;
	public long time;
	public int waiting_time;
	public Integer companyid;
	
	
	public Identification(){
		
	}
	
	
	
	public Identification(Integer id, String name, long time, int waiting_time,
			Integer companyid) {
		super();
		this.id = id;
		this.name = name;
		this.time = time;
		this.waiting_time = waiting_time;
		this.companyid = companyid;
	}



	public static Finder find = new Finder(Integer.class, Identification.class);
	
	
	// Overrides comparable to sort the idents.
	@Override
	public int compareTo(Object obj){
		
		Identification ident = (Identification) obj;
		//first co. object
		Company company = (Company) Company.find.byId(this.companyid);
		//second company object
		Company company1 = (Company) Company.find.byId(ident.companyid);
		
		//Calls getLowerTime to calculate the most urgent ident
		float val =  getlowerTime(company.sla_percentage,company.sla_time,company.current_sla_percentage,this.waiting_time) - getlowerTime(company1.sla_percentage,company1.sla_time,company1.current_sla_percentage,ident.waiting_time);
		
		if(val >0){
			return 1;
		}else{
			return -1;
		}
		
	}
	
	
	//(sla_time - waiting_time) -- How much time left to comlete SLA.
	//(sla_time*(1-current_sla_time)/sla_percent) -- How much task left to complete
	// value -- less time left as per task to complete
	public float getlowerTime(float sla_percent,int sla_time,float current_sla_time,int waiting_time){
		
		
		float value = (sla_time - waiting_time) - (sla_time*(1-current_sla_time)/sla_percent);
		return value;
		
	}
	

}
