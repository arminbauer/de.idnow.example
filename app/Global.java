import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import play.Application;
import play.GlobalSettings;
import play.libs.Json;

public class Global extends GlobalSettings {
	public void onStart(Application app) {
		ObjectMapper mapper = new ObjectMapper().configure(
				SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
		Json.setObjectMapper(mapper);
	}
}
