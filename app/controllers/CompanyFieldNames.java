package controllers;

enum CompanyFieldNames {
	
	NAME("name"),
	ID("id"),
	SLA_TIME("sla_time"),
	SLA_PERCENTAGE("sla_percentage"),
	CURRENT_SLA_PERCENTAGE("current_sla_percentage");
	
	private final String value;
	
	
	private CompanyFieldNames(String value) {
		this.value=value;
	}
	
	public String getValue(){
		return value;

	}
	

}