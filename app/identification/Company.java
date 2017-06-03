package identification;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Company {
    private long id;
    private String name;
    private long slaTime;
    private BigDecimal slaPercentage;
    private BigDecimal currentSla;

    public boolean overdue(Identification identification) {
        return identification.getWaitingTime() > slaTime;
    }
}
