package service;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dao.CompanyRepository;
import dao.IdentificationRepository;
import dto.IdentificationDto;

public class IdentificationServiceImpl implements IdentificationService {

	@Inject
	private CompanyRepository companyRepository;

	@Inject
	private IdentificationRepository identificationRepository;

	@Inject
	private IdentificationConverter converter;

	@Override
	public void save(IdentificationDto identification) {
		if (!companyRepository.existsById(identification.getCompanyId())) {
			throw new CompanyNotExistException("Company do not exists");
		}

		identificationRepository.save(converter.convert(identification));
	}

	@Override
	public List<IdentificationDto> loadOrdered() {
		return identificationRepository.load().stream().sorted(new IdentificationComparator().reversed())
				.map(converter::convertBack).collect(Collectors.toList());
	}

}
