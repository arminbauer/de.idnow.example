package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * identification entity
 *
 * @author Sergii R.
 * @since 17/02/19
 */
@Entity
@Table(name = "identification")
public class IdentificationEntity extends Model {

    @Id
    private Long id;
    private String name;
    private Long time;
    private Long waitingTime;
    @ManyToOne
    private CompanyEntity companyEntity;
    private IdentificationStatus identificationStatus;

    public IdentificationEntity() {
    }

    private IdentificationEntity(Long id, String name, Long time, Long waitingTime, CompanyEntity companyEntity, IdentificationStatus identificationStatus) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.waitingTime = waitingTime;
        this.companyEntity = companyEntity;
        this.identificationStatus = identificationStatus;
    }

    public static IdentificationEntityBuilder builder() {
        return new IdentificationEntityBuilder();
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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Long waitingTime) {
        this.waitingTime = waitingTime;
    }

    public CompanyEntity getCompanyEntity() {
        return companyEntity;
    }

    public void setCompanyEntity(CompanyEntity companyEntity) {
        this.companyEntity = companyEntity;
    }

    public IdentificationStatus getIdentificationStatus() {
        return identificationStatus;
    }

    public void setIdentificationStatus(IdentificationStatus identificationStatus) {
        this.identificationStatus = identificationStatus;
    }

    public static class IdentificationEntityBuilder {
        private Long id;
        private String name;
        private Long time;
        private Long waitingTime;
        private CompanyEntity companyEntity;
        private IdentificationStatus identificationStatus;

        IdentificationEntityBuilder() {
        }

        public IdentificationEntityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public IdentificationEntityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public IdentificationEntityBuilder time(Long time) {
            this.time = time;
            return this;
        }

        public IdentificationEntityBuilder waitingTime(Long waitingTime) {
            this.waitingTime = waitingTime;
            return this;
        }

        public IdentificationEntityBuilder companyEntity(CompanyEntity companyEntity) {
            this.companyEntity = companyEntity;
            return this;
        }

        public IdentificationEntityBuilder identificationStatus(IdentificationStatus identificationStatus) {
            this.identificationStatus = identificationStatus;
            return this;
        }

        public IdentificationEntity build() {
            return new IdentificationEntity(id, name, time, waitingTime, companyEntity, identificationStatus);
        }

    }
}
