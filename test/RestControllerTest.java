import static org.junit.Assert.*;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.After;
import org.junit.Before;

import play.libs.ws.WS;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class RestControllerTest {

	 static final String POSTCOMPANYURL = "http://localhost:3333/api/v1/postCompany";
	 static final String GETCOMPANYURL = "http://localhost:3333/api/v1/getCompanyData";
	 static final String DELETECOMPANYURL = "http://localhost:3333/api/v1/deleteCompanyData";
	 static final String POSTIDENTURL = "http://localhost:3333/api/v1/postIdentification";
	 static final String GETIDENTURL = "http://localhost:3333/api/v1/getIdentifications";
	 static final String DELETEIDENTURL = "http://localhost:3333/api/v1/deleteIdentificationData";
     static final String BASECOMPANY = "{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}";
     static final String EVERYTHINGDIFFERENTCOMPANY = "{\"id\": 5, \"name\": \"Start Up\", \"sla_time\": 90, \"sla_percentage\": 0.80, \"current_sla_percentage\": 0.86}";
	 static final String SLATIMEDIF = "{\"id\": 2, \"name\": \"Test Shop\", \"sla_time\": 90, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.95}";
	 static final String CURRENTSLAPERCENTDIF = "{\"id\": 3, \"name\": \"Test Office\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.92}";
	 static final String SLAPERCENTDIF = "{\"id\": 4, \"name\": \"Test Home Office\", \"sla_time\": 60, \"sla_percentage\": 0.92, \"current_sla_percentage\": 0.95}";
 	 static final String BASEIDENT = "{\"id\": 1, \"name\": \"Peter Huber\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}";
 	 static final String EVRYTHINGSAMEIDENT = "{\"id\": 6, \"name\": \"Ned Flanders\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 1}";
     static final String MISSINGDATACOMPANY = "{\"id\": 5, \"name\": \"Test Site\", \"sla_time\": 90, \"sla_percentage\": 0.9}";
     static final String DIFFENRTWAITTIMEIDENT = "{\"id\": 2, \"name\": \"Jon Doe\", \"time\": 1435667195, \"waiting_time\": 30, \"companyid\": 1}";
	 static final String MAXMUSTERMAN = "{\"id\": 3, \"name\": \"Max Musterman\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 3}";
	 static final String LISEMUELLER = "{\"id\": 4, \"name\": \"Lise Mueller\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 2}";
	 static final String HOMEIDENT = "{\"id\": 5, \"name\": \"Home Worker\", \"time\": 1435667215, \"waiting_time\": 10, \"companyid\": 4}";
	 static final String CHE = "{\"id\": 6, \"name\": \"Che\", \"time\": 1435667220, \"waiting_time\": 5, \"companyid\": 5}";
	 static final String MISSINGDATAIDENT = "{\"id\": 7, \"name\": \"Castro\", \"time\": 1435667220, \"waiting_time\": 5}";
	
	
	@Before
	public void resetData() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), () -> WS.url(DELETECOMPANYURL).delete().get(10000));
		
		
	}
}
