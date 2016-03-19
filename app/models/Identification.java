package models;

/**
 * @author Manuel Poppe.
 */
public class Identification {

    public String id;
    public String name;
    public Long time;
    public Long waiting_time;
    public String companyid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Long getWaiting_time() {
        return waiting_time;
    }

    public void setWaiting_time(Long waiting_time) {
        this.waiting_time = waiting_time;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    @Override
    public String toString() {
        return "Identification{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", waiting_time=" + waiting_time +
                ", companyid='" + companyid + '\'' +
                '}';
    }
}
