package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Company entity (DBO)
 *
 * @author Sergii R.
 * @since 17/02/19
 */

@Entity
@Table(name = "company")
public class CompanyEntity extends Model {

    @Id
    private Long id;
    private String name;
    private Long slaTime;
    private Float slaPercentage;
    private Float currentSlaPercentage;

    private CompanyEntity(Long id, String name, Long slaTime, Float slaPercentage, Float currentSlaPercentage) {
        this.id = id;
        this.name = name;
        this.slaTime = slaTime;
        this.slaPercentage = slaPercentage;
        this.currentSlaPercentage = currentSlaPercentage;
    }

    public CompanyEntity(){}

    public static CompanyEntityBuilder builder(){
        return new CompanyEntityBuilder();
    }

    public static class CompanyEntityBuilder {
        private Long id;
        private String name;
        private Long slaTime;
        private Float slaPercentage;
        private Float currentSlaPercentage;

        CompanyEntityBuilder(){}

        public CompanyEntityBuilder id(Long id){
            this.id = id;
            return this;
        }

        public CompanyEntityBuilder name(String name){
            this.name = name;
            return this;
        }

        public CompanyEntityBuilder slaTime(Long slaTime){
            this.slaTime = slaTime;
            return this;
        }

        public CompanyEntityBuilder slaPercentage(Float slaPercentage){
            this.slaPercentage = slaPercentage;
            return this;
        }

        public CompanyEntityBuilder currentSlaPercentage(Float currentSlaPercentage){
            this.currentSlaPercentage = currentSlaPercentage;
            return this;
        }

        public CompanyEntity build(){
            return new CompanyEntity(id, name, slaTime, slaPercentage, currentSlaPercentage);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSlaTime() {
        return slaTime;
    }

    public void setSlaTime(Long slaTime) {
        this.slaTime = slaTime;
    }

    public Float getSlaPercentage() {
        return slaPercentage;
    }

    public void setSlaPercentage(Float slaPercentage) {
        this.slaPercentage = slaPercentage;
    }

    public Float getCurrentSlaPercentage() {
        return currentSlaPercentage;
    }

    public void setCurrentSlaPercentage(Float currentSlaPercentage) {
        this.currentSlaPercentage = currentSlaPercentage;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CompanyEntity companyToCheck = (CompanyEntity) obj;
        return Objects.equals(id, companyToCheck.getId())
                && Objects.equals(name, companyToCheck.getName())
                && Objects.equals(slaTime, companyToCheck.getSlaTime())
                && Objects.equals(slaPercentage, companyToCheck.getSlaPercentage())
                && Objects.equals(currentSlaPercentage, companyToCheck.getCurrentSlaPercentage());
    }
}
