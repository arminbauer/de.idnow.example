package util.form_binding;

import play.Application;
import play.GlobalSettings;
import play.data.format.Formatters;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

/**
 * By default Form<T> bindFromRequest(String... allowedFields) can not cast unix time (number) to LocalDateTime.
 * This Implementation fixes this problem
 */
public class GlobalSettingsImpl extends GlobalSettings {

    public void onStart(Application app) {
        Formatters.register(LocalDateTime.class, new Formatters.SimpleFormatter<LocalDateTime>() {
            @Override
            public LocalDateTime parse(String input, Locale l) {
                try {
                    long seconds = Long.valueOf(input);
                    Instant instant = Instant.ofEpochSecond(seconds);
                    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                } catch (Exception ex) {
                    throw new IllegalArgumentException("Wrong number format. Unix format required. Exception message: " + ex.getMessage());
                }
            }

            @Override
            public String print(LocalDateTime localDateTime, Locale l) {
                Long seconds = localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
                return seconds.toString();
            }
        });
    }
}
