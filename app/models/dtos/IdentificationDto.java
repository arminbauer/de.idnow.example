package models.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentificationDto {
	private final long id;
	private final String name;
	private final long time;
	private final int waitingTime;
	private final long companyId;

	@JsonCreator
	public IdentificationDto(
			@JsonProperty("id") long id,
			@JsonProperty("name") String name,
			@JsonProperty("time") long time,
			@JsonProperty("waiting_time") int waitingTime,
			@JsonProperty("companyid") long companyId) {

		this.id = id;
		this.name = name;
		this.time = time;
		this.waitingTime = waitingTime;
		this.companyId = companyId;
	}

	@JsonGetter("id")
	public long getId() {
		return id;
	}

	@JsonGetter("name")
	public String getName() {
		return name;
	}

	@JsonGetter("time")
	public long getTime() {
		return time;
	}

	@JsonGetter("waiting_time")
	public int getWaitingTime() {
		return waitingTime;
	}

	@JsonGetter("companyid")
	public long getCompanyId() {
		return companyId;
	}

	@Override
	public String toString() {
		return "IdentificationDto{" +
				"id=" + id +
				", name='" + name + '\'' +
				", time=" + time +
				", waitingTime=" + waitingTime +
				", companyId=" + companyId +
				'}';
	}
}
