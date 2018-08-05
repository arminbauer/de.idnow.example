package json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.TextNode;
import config.TimeZone;
import play.Logger;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.LocalDateTime;

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
        return getUtc(node.longValue());
      } else if (node instanceof TextNode) {
        return getUtc(Long.valueOf(node.textValue()));
      } else {
        throw new JsonParseException("Cannot parse LocalDateTime from unix timestamp", jsonParser.getCurrentLocation());
      }
    } catch (Exception e) {
      Logger.debug("Got exception while parsing localDateTime", e);
      throw new RuntimeException(e);
    }
  }

  @Nonnull
  private LocalDateTime getUtc(final long timestamp) {
    return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.ZONE);
  }
}
