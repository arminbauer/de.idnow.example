package app;

import com.google.inject.AbstractModule;

import dao.CompanyRepository;
import dao.CompanyRepositoryImpl;
import dao.IdentificationRepository;
import dao.IdentificationRepositoryImpl;
import service.CompanyConverter;
import service.CompanyConverterImpl;
import service.CompanyService;
import service.CompanyServiceImpl;
import service.IdentificationConverter;
import service.IdentificationConverterImpl;
import service.IdentificationService;
import service.IdentificationServiceImpl;

public class ApplicationModule extends AbstractModule {

	protected void configure() {
		bind(CompanyService.class).to(CompanyServiceImpl.class);

		bind(IdentificationService.class).to(IdentificationServiceImpl.class);

		bind(CompanyRepository.class).to(CompanyRepositoryImpl.class);

		bind(IdentificationRepository.class).to(IdentificationRepositoryImpl.class);

		bind(IdentificationConverter.class).to(IdentificationConverterImpl.class);

		bind(CompanyConverter.class).to(CompanyConverterImpl.class);
	}
}