package business.company.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author: hardik
 * @since: 10/8/15
 */

@Entity
@NamedQueries ({
        @NamedQuery(name = "Company.all", query = "SELECT c from Company  c")
})
public class Company {

    @Id
    private Long id;

    private String name;

    private Long  slaTime;

    private Double slaPercentage;

    private Double currentSlaPercent;


    public Double getSLAPercentageUrgency () {

        return (this.currentSlaPercent - this.slaPercentage);
    }

    public Long getId () {

        return id;
    }

    public void setId ( Long id ) {

        this.id = id;
    }

    public String getName () {

        return name;
    }

    public void setName ( String name ) {

        this.name = name;
    }

    public Long getSlaTime () {

        return slaTime;
    }

    public void setSlaTime ( Long slaTime ) {

        this.slaTime = slaTime;
    }

    public Double getSlaPercentage () {

        return slaPercentage;
    }

    public void setSlaPercentage ( Double slaPercentage ) {

        this.slaPercentage = slaPercentage;
    }

    public Double getCurrentSlaPercent () {

        return currentSlaPercent;
    }

    public void setCurrentSlaPercent ( Double currentSlaPercent ) {

        this.currentSlaPercent = currentSlaPercent;
    }
}
