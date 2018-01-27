package models;
import com.fasterxml.jackson.annotation.JsonProperty;
public class Company{
	private int id;
	private String name;
	@JsonProperty("sla_time")
	int SLATime;
	@JsonProperty("sla_percentage")
	float SLAPercentage;
	@JsonProperty("current_sla_percentage")
	float currentSLAPercentage;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSLATime() {
		return SLATime;
	}
	public void setSLATime(int sLATime) {
		SLATime = sLATime;
	}
	public float getSLAPercentage() {
		return SLAPercentage;
	}
	public void setSLAPercentage(float sLAPercentage) {
		SLAPercentage = sLAPercentage;
	}
	public float getCurrentSLAPercentage() {
		return currentSLAPercentage;
	}
	public void setCurrentSLAPercentage(float currentSLAPercentage) {
		this.currentSLAPercentage = currentSLAPercentage;
	}
	@Override
	public int hashCode(){
		return id*99;
	}
	@Override
	public String toString(){
		return "[Company:"+id+":"+name+"]";
	}
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Company)){
			return false;
		}
		Company cObj = (Company)o;
		return id == cObj.getId();
	}
}