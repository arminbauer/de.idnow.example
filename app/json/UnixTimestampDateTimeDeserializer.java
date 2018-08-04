package json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import play.Logger;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
public class UnixTimestampDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
  @Override
  public LocalDateTime deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) {
    try {
      final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
      if (node instanceof NumericNode) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(node.longValue()), ZoneId.of("UTC"));
      } else {
        return null;
      }
    } catch (Exception e) {
      Logger.debug("Got exception while parsing localDateTime", e);
      throw new RuntimeException(e);
    }
  }
}
