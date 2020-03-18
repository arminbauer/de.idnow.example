package service;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.CompanyRepository;
import dto.CompanyDto;

public class CompanyServiceImpl implements CompanyService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Inject
	private CompanyRepository repository;

	@Inject
	private CompanyConverter converter;

	@Override
	public void save(CompanyDto company) {
		log.info("Storing company with id {}", company.getId());
		repository.save(converter.convert(company));
	}

	@Override
	public List<CompanyDto> load() {
		return repository.load().stream().map(converter::convertBack).collect(Collectors.toList());
	}

	@Override
	public CompanyDto loadById(Long id) {
		return converter.convertBack(repository.loadById(id));
	}

	@Override
	public boolean existsById(Long id) {
		return repository.existsById(id);
	}

}
