package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import java.util.Objects;

import static repositories.IdentificationRepository.LIST_ORDERED_IDENTIFICATIONS;

@Entity
@NamedQuery(
    name = LIST_ORDERED_IDENTIFICATIONS,
    query = "select i from Identification i " +
            "join i.company c " +
            "where i.pending = true " +
            "order by c.slaTime asc, c.currentSlaPercentage asc, i.waitingTime desc"
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Identification {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Long time;

    @JsonProperty("waiting_time")
    private Integer waitingTime;

    private boolean pending;

    @ManyToOne
    private Company company;

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean open) {
        this.pending = open;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identification that = (Identification) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Identification{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
