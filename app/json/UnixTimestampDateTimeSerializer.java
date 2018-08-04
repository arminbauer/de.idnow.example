package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
public class UnixTimestampDateTimeSerializer extends JsonSerializer<LocalDateTime> {
  @Override
  public void serialize(final LocalDateTime localDateTime, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(String.valueOf(localDateTime.atZone(ZoneId.of("UTC")).toEpochSecond()));
  }
}
