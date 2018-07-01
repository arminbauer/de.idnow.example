package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import util.json.LocalDateTimeDeserializer;
import util.json.LocalDateTimeSerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "identification")
public class Identification extends Model {

    @Id
    public Long id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public LocalDateTime time;

    @Column(nullable = false)
    @JsonProperty("waiting_time")
    public Integer waitingTime;

    @Column(nullable = false)
    @JsonProperty("companyid")
    public Long companyId;

    public Identification(String name, LocalDateTime time, Integer waitingTime, Long companyId) {
        this.name = name;
        this.time = time;
        this.waitingTime = waitingTime;
        this.companyId = companyId;
    }

    public Identification(Long id, String name, LocalDateTime time, Integer waitingTime, Long companyId) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.waitingTime = waitingTime;
        this.companyId = companyId;
    }


}
