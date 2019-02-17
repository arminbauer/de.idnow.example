package api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Company api model
 * It represents the request of IDnow customer
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class Company {
    private Long id;
    private String name;

    @JsonProperty("sla_time")
    private Long slaTime;

    @JsonProperty("sla_percentage")
    private Float slaPercentage;

    @JsonProperty("current_sla_percentage")
    private Float currentSlaPercentage;

    public Company() {
    }

    //TODO:: better to change it with Lombok
    private Company(Long id, String name, Long slaTime, Float slaPercentage, Float currentSlaPercentage) {
        this.id = id;
        this.name = name;
        this.slaTime = slaTime;
        this.slaPercentage = slaPercentage;
        this.currentSlaPercentage = currentSlaPercentage;
    }

    public static CompanyBuilder builder() {
        return new CompanyBuilder();
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

    public static class CompanyBuilder {
        private Long id;
        private String name;
        private Long slaTime;
        private Float slaPercentage;
        private Float currentSlaPercentage;

        CompanyBuilder() {
        }

        public CompanyBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CompanyBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CompanyBuilder slaTime(Long slaTime) {
            this.slaTime = slaTime;
            return this;
        }

        public CompanyBuilder slaPercentage(Float slaPercentage) {
            this.slaPercentage = slaPercentage;
            return this;
        }

        public CompanyBuilder currentSlaPercentage(Float currentSlaPercentage) {
            this.currentSlaPercentage = currentSlaPercentage;
            return this;
        }

        public Company build() {
            return new Company(id, name, slaTime, slaPercentage, currentSlaPercentage);
        }
    }
}
