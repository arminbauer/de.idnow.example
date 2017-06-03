package identification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@Builder
public class Identification {
    private long id;
    private long time;
    private long companyId;

    public long getWaitingTime() {
        return ZonedDateTime.now().toEpochSecond() - time;
    }
}
