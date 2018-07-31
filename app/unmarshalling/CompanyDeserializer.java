package unmarshalling;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import model.Company;

import java.io.IOException;

public class CompanyDeserializer extends StdDeserializer<Company> {

  public CompanyDeserializer() {
    this(null);
  }

  public CompanyDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Company deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode node = jp.getCodec().readTree(jp);
    long id = node.get("id").asLong();
    String name = node.get("name").asText();
    long time = node.get("sla_time").asLong();
    float waitingTime = (float) node.get("sla_percentage").asDouble();
    float companyId = (float) node.get("current_sla_percentage").asDouble();

    return new Company(id, name, time, waitingTime, companyId);
  }
}
