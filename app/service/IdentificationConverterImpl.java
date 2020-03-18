package service;

import javax.inject.Inject;

import dao.CompanyRepository;
import domain.Identification;
import dto.IdentificationDto;

public class IdentificationConverterImpl implements IdentificationConverter {

	private final CompanyRepository companyRepository;

	@Inject
	public IdentificationConverterImpl(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	@Override
	public IdentificationDto convertBack(Identification domain) {
		if (domain == null) {
			return null;
		}
		return IdentificationDto.builder().withCompanyId(domain.company.id).withId(domain.id).withName(domain.name)
				.withTime(domain.time).withWaitingTime(domain.waitingTime).build();
	}

	@Override
	public Identification convert(IdentificationDto dto) {
		if (dto == null) {
			return null;
		}
		final Identification identification = new Identification();
		identification.id = dto.getId();
		identification.name = dto.getName();
		identification.time = dto.getTime();
		identification.waitingTime = dto.getWaitingTime();
		identification.company = companyRepository.loadById(dto.getCompanyId());

		return identification;
	}

}
