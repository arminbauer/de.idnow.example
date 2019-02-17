package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Weight settings entity
 *
 * @author Sergii R.
 * @since 17/02/19
 */

@Entity
@Table(name = "settings")
public class WeightSettingsEntity {
    @Id
    private Long id;
    private Float slaDifferenceWeight;
    private Float slaWeight;
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
