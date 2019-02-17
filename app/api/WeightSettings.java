package api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Weight settings api model
 * It represents the request of weighting settings:
 * slaDifferenceWeight - weight for sla difference ratio ratio calculation
 * slaWeight - weight for sla percentage ratio weight calculation
 * waitingTimeWeight - weight for time difference ratio weight calculation
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class WeightSettings {
    private Long id;
    @JsonProperty("sla_difference_weight")
    private Float slaDifferenceWeight;
    @JsonProperty("sla_weight")
    private Float slaWeight;
    @JsonProperty("waiting_time_weight")
    private Float waitingTimeWeight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getSlaDifferenceWeight() {
        return slaDifferenceWeight;
    }

    public void setSlaDifferenceWeight(Float slaDifferenceWeight) {
        this.slaDifferenceWeight = slaDifferenceWeight;
    }

    public Float getSlaWeight() {
        return slaWeight;
    }

    public void setSlaWeight(Float slaWeight) {
        this.slaWeight = slaWeight;
    }

    public Float getWaitingTimeWeight() {
        return waitingTimeWeight;
    }

    public void setWaitingTimeWeight(Float waitingTimeWeight) {
        this.waitingTimeWeight = waitingTimeWeight;
    }
}
