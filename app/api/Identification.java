package api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Identification api model
 * It represents the request of a user to be identified by IDnow
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class Identification {

    private Long id;
    private String name;
    private Long time;

    @JsonProperty("waiting_time")
    private Long waitingTime;

    private Long companyId;

    public Identification() {
    }

    public Identification(Long id, String name, Long time, Long waitingTime, Long companyId) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.waitingTime = waitingTime;
        this.companyId = companyId;
    }

    public static IdentificationBuilder builder() {
        return new IdentificationBuilder();
    }

    public static class IdentificationBuilder {
        private Long id;
        private String name;
        private Long time;
        private Long waitingTime;
        private Long companyId;

        public IdentificationBuilder() {
        }

        public IdentificationBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public IdentificationBuilder name(String name) {
            this.name = name;
            return this;
        }

        public IdentificationBuilder time(Long time) {
            this.time = time;
            return this;
        }

        public IdentificationBuilder waitingTime(Long waitingTime) {
            this.waitingTime = waitingTime;
            return this;
        }

        public IdentificationBuilder companyId(Long companyId) {
            this.companyId = companyId;
            return this;
        }

        public Identification build() {
            return new Identification(id, name, time, waitingTime, companyId);
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
