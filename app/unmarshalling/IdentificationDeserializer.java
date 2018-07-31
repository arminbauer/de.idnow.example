package unmarshalling;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import model.Identification;

import java.io.IOException;

public class IdentificationDeserializer extends StdDeserializer<Identification> {

  public IdentificationDeserializer() {
    this(null);
  }

  public IdentificationDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Identification deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode node = jp.getCodec().readTree(jp);
    long id = node.get("id").asLong();
    String name = node.get("name").asText();
    long time = node.get("time").asLong();
    long waitingTime = node.get("waiting_time").asLong();
    long companyId = node.get("companyid").asLong();

    return new Identification(id, name, time, waitingTime, companyId);
  }
}
