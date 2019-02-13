package model;

import play.Logger;
import service.DBService;

import java.util.Comparator;
import java.util.Date;

public class Identification implements Comparable<Identification> {
    private String id;
    private String name;
    private java.util.Date time;
    private int waiting_time;
    private String companyid;

    public Identification() {
    }

    /*
        * Id: The unique ID of the identification
        * Name: Name of the user
        * Time: The time when this identification request was started by the user (Unix format)
        * Waiting_time: The current waiting time of the identification in seconds (since the user started)
        * Companyid: The ID of the company to which this identification belongs
        * */
    public Identification(String id, String name, java.util.Date time, int waiting_time, String companyid) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.waiting_time = waiting_time;
        this.companyid = companyid;
    }

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getWaiting_time() {
        return waiting_time;
    }

    public void setWaiting_time(int waiting_time) {
        this.waiting_time = waiting_time;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }


    @Override
    public int compareTo(Identification o1) {

        //this method tells which identification is more urgent.

        Identification o2 = this;

        //identifications from same company
        if (o1.getCompanyid().equals(o2.getCompanyid())) {
            Logger.debug("Comparing waiting time " + o1.getWaiting_time() + " with " + o2.getWaiting_time());
            return Integer.compare(o2.getWaiting_time(), o1.getWaiting_time());
        }

        //identifications not from same company
        if (o1.getCurrent_sla_percentage() == o2.getCurrent_sla_percentage()) {
            if (o1.getSla_percentage() == o2.getSla_percentage()) {
                Logger.debug("Comparing Sla time " + o1.getSla_time() + " with " + o2.getSla_time());
                return Double.compare(o2.getSla_time(), o1.getSla_time());
            } else {
                Logger.debug("Comparing Sla percentage time " + o1.getSla_percentage() + " with " + o2.getSla_percentage());
                return Double.compare(o2.getSla_percentage(), o1.getSla_percentage());
            }
        } else {
            if (o1.getSla_time() == o2.getSla_time()) {
                Logger.debug("Comparing current Sla percentage time " + o1.getCurrent_sla_percentage() + " with " + o2.getCurrent_sla_percentage());
                return Double.compare(o1.getCurrent_sla_percentage(), o2.getCurrent_sla_percentage());
            } else {
                Logger.debug("Comparing Sla time " + o1.getSla_time() + " with " + o2.getSla_time());
                return Double.compare(o2.getSla_time(), o1.getSla_time());
            }
        }

    }

    public int getSla_time() {
        return DBService.getInstance().getCompanyByID(companyid).getSla_time();
    }

    public double getSla_percentage() {
        return DBService.getInstance().getCompanyByID(companyid).getSla_percentage();
    }

    public double getCurrent_sla_percentage() {
        return DBService.getInstance().getCompanyByID(companyid).getCurrent_sla_percentage();
    }

}