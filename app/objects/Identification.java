package objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Identification implements Comparable<Identification>{

    @JsonProperty("id")
    private Long id;

    @JsonProperty("companyid")
    private long companyId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("time")
    private long time;

    @JsonProperty("waiting_time")
    private long waitingTime;

    @JsonIgnore
    private Company company;

    public Identification(){}

    public Identification(Long id, long companyId, String name, long time, long waitingTime){
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.time = time;
        this.waitingTime = waitingTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public int compareTo(Identification identification) {
        if (this.company == null || identification.company == null){
            try {
                throw new Exception("No Company assigned");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.company.compareTo(identification.company) != 0){
            return this.company.compareTo(identification.company);
        }
        return Long.compare(this.getWaitingTime(), identification.getWaitingTime());
    }
}
