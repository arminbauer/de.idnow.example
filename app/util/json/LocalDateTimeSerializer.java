package util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator jg,
                          SerializerProvider sp) throws IOException {
        long seconds = dateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
        jg.writeString(String.valueOf(seconds));
    }
}