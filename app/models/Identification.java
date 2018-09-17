package models;

import java.util.Date;

public class Identification {
	
   
   private String id;
   private String customerName;
   private long startTime; //time when the user started the ID 
   private long waitingtimeSecs; //current waiting time in seconds since the user started
   private String companyId;// this company id will be attached to the id
   

   public String getId(){
   	return this.id;
   }

   public void SetId(String id){
   	this.id = id;
   }

   public long getStartTime() {
	return startTime;
}

public void setStartTime(long startTime) {
	this.startTime = startTime;
}

public long getWaitingtimeSecs() {
	return waitingtimeSecs;
}

public void setWaitingtimeSecs(long waitingtimeSecs) {
	this.waitingtimeSecs = waitingtimeSecs;
}

public String getCompanyId() {
	return companyId;
}

public void setCompanyId(String companyId) {
	this.companyId = companyId;
}

public void setId(String id) {
	this.id = id;
}

public String getCustomerName(){
   	  return this.customerName;
   }

   public void setCustomerName(String customerName) {
   	this.customerName = customerName;
   }
   

}