package controller;

public final class TestHelper {
	

	
	public static final String ENDPOINT_ADD_COMPANY = "http://localhost:3333/api/v1/addCompany";
	
	public static final String ENDPOINT_START_IDENTIFICATION = "http://localhost:3333/api/v1/startIdentification";
	
	public static final String ENDPOINT_IDENTIFICATION = "http://localhost:3333/api/v1/identifications";
	
	public static final String JSON_COMPANY_NO_ID =
			" {"
			+ "\"name\": \"Test Bank\", "
			+ "\"sla_time\": 60, "
			+ "\"sla_percentage\": 0.9, "
			+ "\"current_sla_percentage\": 0.95}";
	
	public static final String JSON_COMPANY_1 =
			" {\"id\": 1, "
			+ "\"name\": \"Test Bank\", "
			+ "\"sla_time\": 60, "
			+ "\"sla_percentage\": 0.9, "
			+ "\"current_sla_percentage\": 0.95}";
	
	public static final String JSON_IDENTIFICATION_1 =
			 "{\"id\": 1, "
			+ "\"name\": \"Peter Huber\", "
			+ "\"time\": 1435667215, "
			+ "\"waiting_time\": 10, "
			+ "\"companyid\": 1}";

	public static final String JSON_IDENTIFICATION_2 =
			 "{\"id\": 2, "
			+ "\"name\": \"Robert Miller\", "
			+ "\"time\": 1435667215, "
			+ "\"waiting_time\": 45, "
			+ "\"companyid\": 1}";
	
	// Example 1
	
	public static final String JSON_EXAMPLE_1_COMPANY_1 =
			" {\"id\": 1, "
			+ "\"name\": \"Test Bank\", "
			+ "\"sla_time\": 60, "
			+ "\"sla_percentage\": 0.9, "
			+ "\"current_sla_percentage\": 0.95}";
	
	public static final String JSON_EXAMPLE_1_IDENTIFICATION_1 =
			 "{\"id\": 1, "
			+ "\"name\": \"Peter Huber\", "
			+ "\"time\": 1435667215, "
			+ "\"waiting_time\": 30, "
			+ "\"companyid\": 1}";
	
	public static final String JSON_EXAMPLE_1_IDENTIFICATION_2 =
			 "{\"id\": 2, "
			+ "\"name\": \"Peter B\", "
			+ "\"time\": 1435667215, "
			+ "\"waiting_time\": 45, "
			+ "\"companyid\": 1}";

	// Example 2
	public static final String JSON_EXAMPLE_2_COMPANY_1 =
			" {\"id\": 1, "
			+ "\"name\": \"Test Bank\", "
			+ "\"sla_time\": 60, "
			+ "\"sla_percentage\": 0.9, "
			+ "\"current_sla_percentage\": 0.95}";
	
	public static final String JSON_EXAMPLE_2_COMPANY_2 =
			" {\"id\": 2, "
			+ "\"name\": \"Test Bank\", "
			+ "\"sla_time\": 60, "
			+ "\"sla_percentage\": 0.9, "
			+ "\"current_sla_percentage\": 0.90}";
	
	public static final String JSON_EXAMPLE_2_IDENTIFICATION_1 =
			 "{\"id\": 1, "
			+ "\"name\": \"Peter Huber\", "
			+ "\"time\": 1435667215, "
			+ "\"waiting_time\": 30, "
			+ "\"companyid\": 1}";
	
	public static final String JSON_EXAMPLE_2_IDENTIFICATION_2 =
			 "{\"id\": 2, "
			+ "\"name\": \"Peter B\", "
			+ "\"time\": 1435667215, "
			+ "\"waiting_time\": 30, "
			+ "\"companyid\": 2}";

	// Example 3
		public static final String JSON_EXAMPLE_3_COMPANY_1 =
				" {\"id\": 1, "
				+ "\"name\": \"Test Bank\", "
				+ "\"sla_time\": 60, "
				+ "\"sla_percentage\": 0.9, "
				+ "\"current_sla_percentage\": 0.95}";
		
		public static final String JSON_EXAMPLE_3_COMPANY_2 =
				" {\"id\": 2, "
				+ "\"name\": \"Test Bank\", "
				+ "\"sla_time\": 120, "
				+ "\"sla_percentage\": 0.8, "
				+ "\"current_sla_percentage\": 0.95}";
		
		public static final String JSON_EXAMPLE_3_IDENTIFICATION_1 =
				 "{\"id\": 1, "
				+ "\"name\": \"Peter Huber\", "
				+ "\"time\": 1435667215, "
				+ "\"waiting_time\": 30, "
				+ "\"companyid\": 1}";
		
		public static final String JSON_EXAMPLE_3_IDENTIFICATION_2 =
				 "{\"id\": 2, "
				+ "\"name\": \"Peter B\", "
				+ "\"time\": 1435667215, "
				+ "\"waiting_time\": 30, "
				+ "\"companyid\": 2}";
	
		// Example 4
		public static final String JSON_EXAMPLE_4_COMPANY_1 =
				" {\"id\": 1, "
				+ "\"name\": \"Test Bank\", "
				+ "\"sla_time\": 60, "
				+ "\"sla_percentage\": 0.9, "
				+ "\"current_sla_percentage\": 0.95}";
		
		public static final String JSON_EXAMPLE_4_COMPANY_2 =
				" {\"id\": 2, "
				+ "\"name\": \"Test Bank\", "
				+ "\"sla_time\": 120, "
				+ "\"sla_percentage\": 0.8, "
				+ "\"current_sla_percentage\": 0.80}";
		
		public static final String JSON_EXAMPLE_4_IDENTIFICATION_1 =
				 "{\"id\": 1, "
				+ "\"name\": \"Peter Huber\", "
				+ "\"time\": 1435667215, "
				+ "\"waiting_time\": 45, "
				+ "\"companyid\": 1}";
		
		public static final String JSON_EXAMPLE_4_IDENTIFICATION_2 =
				 "{\"id\": 2, "
				+ "\"name\": \"Peter B\", "
				+ "\"time\": 1435667215, "
				+ "\"waiting_time\": 30, "
				+ "\"companyid\": 2}";
}
