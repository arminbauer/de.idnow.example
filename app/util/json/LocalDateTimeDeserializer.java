package util.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ValueNode;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        try {
            ObjectCodec codec = jp.getCodec();
            ValueNode node = codec.readTree(jp);
            if (node == null) {
                return null;
            }
            long seconds;
            if (node instanceof NumericNode) {
                seconds = node.intValue();
            } else {
                String textValue = node.textValue();
                seconds = Long.valueOf(textValue);
            }
            Instant instant = Instant.ofEpochSecond(seconds);
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        } catch (Exception ex) {
            throw new IllegalArgumentException("Wrong number format. Unix format required. Exception message: " + ex.getMessage());
        }
    }
}
