package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The data transfer object of an identification.
 *
 * @author Markus Panholzer <markus.panholzer@eforce21.com>
 * @since 31.09.2017
 */
public class IdentificationDTO {

	/** The unique ID of the identification. */
	private String id;

	/** Name of the user. */
	private String name;

	/**
	 * The time when this identification request was started by the user (Unix
	 * format).
	 */
	private long time;

	/**
	 * The current waiting time of the identification in seconds (since the user
	 * started).
	 */
	@JsonProperty("waiting_time")
	private long waitingTime;

	/** The ID of the company to which this identification belongs. */
	@JsonProperty("companyid")
	private String companyId;

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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "[id: " + this.id + ", name: " + this.name + ", time: " + this.time + ", waitingTime: "
				+ this.waitingTime + ", company: " + this.companyId + "]";
	}

}
