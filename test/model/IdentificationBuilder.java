package model;

import repository.CompanyRepository;

public class IdentificationBuilder {
	private Long id = 1L;
	private String name = "Identification 1";
	private Long time = 1458503587L;
	private Integer waitingTime = 60;
	private Long companyId = 1L;
	private Company company = new CompanyBuilder().build();
	private CompanyRepository companyRepository;

	public Identification build() {
		Identification identification = new Identification();
		identification.setId(id);
		identification.setName(name);
		identification.setTime(time);
		identification.setWaitingTime(waitingTime);
		identification.setCompanyId(companyId);
		identification.setCompany(company);
		identification.setCompanyRepository(companyRepository);
		return identification;
	}

	public IdentificationBuilder() {
	}

	public IdentificationBuilder(Long id) {
		this.id = id;
	}

	public IdentificationBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public IdentificationBuilder withTime(Long time) {
		this.time = time;
		return this;
	}

	public IdentificationBuilder withWaitingTime(Integer waitingTime) {
		this.waitingTime = waitingTime;
		return this;
	}

	public IdentificationBuilder withCompanyId(Long companyId) {
		this.companyId = companyId;
		return this;
	}

	public IdentificationBuilder withCompany(Company company) {
		this.company = company;
		return this;
	}

	public IdentificationBuilder withCompanyRepository(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
		return this;
	}
}
