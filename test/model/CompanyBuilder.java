package model;

public class CompanyBuilder {

	private Long id = 1L;
	private String name = "Company 1";
	private Integer slaTime = 60;
	private Double slaPercentage = 0.9;
	private Double currentSlaPercentage = 0.95;

	public Company build() {
		Company company = new Company();
		company.setId(id);
		company.setName(name);
		company.setSlaTime(slaTime);
		company.setSlaPercentage(slaPercentage);
		company.setCurrentSlaPercentage(currentSlaPercentage);
		return company;
	}

	public CompanyBuilder() {
	}

	public CompanyBuilder(Long id) {
		this.id = id;
	}

	public CompanyBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public CompanyBuilder withSlaTime(Integer slaTime) {
		this.slaTime = slaTime;
		return this;
	}

	public CompanyBuilder withSlaPercentage(Double slaPercentage) {
		this.slaPercentage = slaPercentage;
		return this;
	}

	public CompanyBuilder withCurrentSlaPercentage(Double currentSlaPercentage) {
		this.currentSlaPercentage = currentSlaPercentage;
		return this;
	}
}
