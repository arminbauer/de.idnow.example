package dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentificationDto {

	private Long id;
	private String name;
	private Date time;
	@JsonProperty("waiting_time")
	private int waitingTime;
	@JsonProperty("companyid")
	private Long companyId;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getTime() {
		return time;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private final IdentificationDto object = new IdentificationDto();

		public IdentificationDto build() {
			return object;
		}

		public Builder withId(Long id) {
			object.id = id;
			return this;
		}

		public Builder withName(String name) {
			object.name = name;
			return this;
		}

		public Builder withTime(Date time) {
			object.time = time;
			return this;
		}

		public Builder withWaitingTime(int waitingTime) {
			object.waitingTime = waitingTime;
			return this;
		}

		public Builder withCompanyId(Long companyId) {
			object.companyId = companyId;
			return this;
		}

	}
}