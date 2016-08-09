package models.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyDto {
	private final long id;
	private final String name;
	private final int slaTime;
	private final float slaPercentage;
	private final float currentSlaPercentage;

	@JsonCreator
	public CompanyDto(
			@JsonProperty(value = "id") long id,
			@JsonProperty(value = "name") String name,
			@JsonProperty(value = "sla_time") int slaTime,
			@JsonProperty(value = "sla_percentage") float slaPercentage,
			@JsonProperty(value = "current_sla_percentage") float currentSlaPercentage) {

		this.id = id;
		this.name = name;
		this.slaTime = slaTime;
		this.slaPercentage = slaPercentage;
		this.currentSlaPercentage = currentSlaPercentage;
	}

	@JsonGetter("id")
	public long getId() {
		return id;
	}

	@JsonGetter("name")
	public String getName() {
		return name;
	}

	@JsonGetter("sla_time")
	public int getSlaTime() {
		return slaTime;
	}

	@JsonGetter("sla_percentage")
	public float getSlaPercentage() {
		return slaPercentage;
	}

	@JsonGetter("current_sla_percentage")
	public float getCurrentSlaPercentage() {
		return currentSlaPercentage;
	}

	@Override
	public String toString() {
		return "CompanyDto{" +
				"id=" + id +
				", name='" + name + '\'' +
				", slaTime=" + slaTime +
				", slaPercentage=" + slaPercentage +
				", currentSlaPercentage=" + currentSlaPercentage +
				'}';
	}
}
