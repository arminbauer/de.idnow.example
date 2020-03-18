package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyDto {

	private Long id;

	private String name;

	@JsonProperty("sla_time") 
	private int slaTime;

	@JsonProperty("sla_percentage") 
	private float slaPercentage;

	@JsonProperty("current_sla_percentage") 
	private float currentSlaPercentage;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getSlaTime() {
		return slaTime;
	}

	public float getSlaPercentage() {
		return slaPercentage;
	}

	public float getCurrentSlaPercentage() {
		return currentSlaPercentage;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private final CompanyDto object = new CompanyDto();

		public CompanyDto build() {
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

		public Builder withSlaTime(int slaTime) {
			object.slaTime = slaTime;
			return this;
		}

		public Builder withSlaPercentage(float slaPercentage) {
			object.slaPercentage = slaPercentage;
			return this;
		}

		public Builder withCurrentSlaPercentage(float currentSlaPercentage) {
			object.currentSlaPercentage = currentSlaPercentage;
			return this;
		}

	}
}
