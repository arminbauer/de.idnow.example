package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by ebajrami on 4/22/16.
 */
@Entity
public class Identification {

    /**
     * The unique ID of the identification
     */
    @Id
    private int id;

    /**
     * Name of the user
     */
    private String name;

    /**
     * The time when this identification request was started by the user (Unix format)
     */
    private long time;

    /**
     * The current waiting time of the identification in seconds (since the user started)
     */
    private long waitingTime;

    /**
     * The ID of the company to which this identification belongs
     */
    @ManyToOne
    private Company company;

}
