package service;

import domain.Company;
import dto.CompanyDto;

public class CompanyConverterImpl implements CompanyConverter {

	@Override
	public CompanyDto convertBack(Company domain) {
		if (domain == null) {
			return null;
		}
		return CompanyDto.builder().withId(domain.id).withName(domain.name).withSlaTime(domain.slaTime)
            .withCurrentSlaPercentage(domain.currentSlaPercentage).withSlaPercentage(domain.slaPercentage).build();
	}

	@Override
	public Company convert(CompanyDto dto) {
		if (dto == null) {
			return null;
		}
		final Company company = new Company();
		company.id = dto.getId();
		company.name = dto.getName();
		company.slaTime = dto.getSlaTime();
        company.currentSlaPercentage = dto.getCurrentSlaPercentage();
		company.slaPercentage = dto.getSlaPercentage();
		return company;
	}

}
