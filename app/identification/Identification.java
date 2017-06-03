package identification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Identification {
    private long id;
    private String name;
    private long time;
    private long companyId;
    private long waitingTime;
}
