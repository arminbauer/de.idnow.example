package models;

/**
 * Model class for an Identification, which is comparable by it's priority.
 *
 * @author Markus Panholzer <markus.panholzer@eforce21.com>
 * @since 31.09.2017
 */
public class Identification implements Comparable<Identification> {

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
	private long waitingTime;

	/** The company to which this identification belongs. */
	private Company company;

	/** The calculated priority of the identification request. */
	private double priority;

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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public double getPriority() {
		return priority;
	}

	public void setPriority(double priority) {
		this.priority = priority;
	}

	@Override
	public int compareTo(Identification object) {
		return ((Double) object.getPriority()).compareTo((Double) priority);
	}

}
