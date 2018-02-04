import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import play.Application;
import play.GlobalSettings;
import play.libs.Json;

/**
 * @author prasa on 03-02-2018
 * @project de.idnow.example
 */
public class Global extends GlobalSettings {
    public void onStart(Application app) {
        ObjectMapper mapper = new ObjectMapper().configure(
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        Json.setObjectMapper(mapper);
    }
}
