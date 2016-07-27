package controllers;

enum IdentificationFieldNames {
	
	NAME("name"),
	ID("id"),
	WAITING_TIME("waiting_time"),
	COMPANY_ID("companyid");
	
	private final String value;
	
	
	private IdentificationFieldNames(String value) {
		this.value=value;
	}
	
	public String getValue(){
		return value;

	}
	

}