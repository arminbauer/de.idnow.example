package business.identification.entity;

import business.company.entity.Company;
import data.IdentificationTO;
import play.db.jpa.JPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author: hardik
 * @since: 10/8/15
 */

@Entity
@NamedQueries ({
        @NamedQuery (name = "Identification.all", query = "SELECT i from Identification i")
})
public class Identification {

    @Id
    private Long id;

    private String name;

    private Long time;

    private Long waitingTime;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    public static Identification fromTO( IdentificationTO identificationTO) {

        Identification bo = new Identification ();
        bo.setId (identificationTO.getId ());
        bo.setName (identificationTO.getName ());
        bo.setTime (identificationTO.getTime ());
        bo.setWaitingTime (identificationTO.getWaitingTime ());
        Company company = JPA.em ().find(Company.class,identificationTO.getCompanyId ());
        if ( null == company) {

            throw new RuntimeException ("Company with id=" + identificationTO.getId () + " not found");
        }

        bo.setCompany (company);
        return bo;
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

    public Long getTime () {

        return time;
    }

    public void setTime ( Long time ) {

        this.time = time;
    }

    public Long getWaitingTime () {

        return waitingTime;
    }

    public void setWaitingTime ( Long waitingTime ) {

        this.waitingTime = waitingTime;
    }

    public Company getCompany () {

        return company;
    }

    public void setCompany ( Company company ) {

        this.company = company;
    }
}
