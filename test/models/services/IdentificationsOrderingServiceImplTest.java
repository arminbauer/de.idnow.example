package models.services;

import models.dtos.CompanyDto;
import models.dtos.IdentificationDto;
import models.repositories.CompaniesRepository;
import models.repositories.IdentificationsRepository;
import models.services.IdentificationsOrderingServiceImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IdentificationsOrderingServiceImplTest {
	@Test
	public void example1()  {
		CompaniesRepository companiesRepository = mock(CompaniesRepository.class);
		when(companiesRepository.find(1)).thenReturn(new CompanyDto(1, "1", 60, 0.9f, 0.95f));

		IdentificationsRepository identificationsRepository = mock(IdentificationsRepository.class);
		when(identificationsRepository.findAll()).thenReturn(Arrays.asList(
				new IdentificationDto(1, "1", 0, 30, 1),
				new IdentificationDto(2, "2", 0, 45, 1))
		);

		IdentificationsOrderingServiceImpl service = new IdentificationsOrderingServiceImpl(
				companiesRepository, identificationsRepository);
		List<IdentificationDto> actual = service.getOrderedIdentifications();
		assertEquals(2, actual.get(0).getId());
		assertEquals(1, actual.get(1).getId());
	}

	@Test
	public void example2()  {
		CompaniesRepository companiesRepository = mock(CompaniesRepository.class);
		when(companiesRepository.find(1)).thenReturn(new CompanyDto(1, "1", 60, 0.9f, 0.95f));
		when(companiesRepository.find(2)).thenReturn(new CompanyDto(2, "2", 60, 0.9f, 0.90f));

		IdentificationsRepository identificationsRepository = mock(IdentificationsRepository.class);
		when(identificationsRepository.findAll()).thenReturn(Arrays.asList(
				new IdentificationDto(1, "1", 0, 30, 1),
				new IdentificationDto(2, "2", 0, 30, 2))
		);

		IdentificationsOrderingServiceImpl service = new IdentificationsOrderingServiceImpl(
				companiesRepository, identificationsRepository);
		List<IdentificationDto> actual = service.getOrderedIdentifications();
		assertEquals(2, actual.get(0).getId());
		assertEquals(1, actual.get(1).getId());
	}

	@Test
	public void example3()  {
		CompaniesRepository companiesRepository = mock(CompaniesRepository.class);
		when(companiesRepository.find(1)).thenReturn(new CompanyDto(1, "1", 60, 0.9f, 0.95f));
		when(companiesRepository.find(2)).thenReturn(new CompanyDto(2, "2", 120, 0.8f, 0.95f));

		IdentificationsRepository identificationsRepository = mock(IdentificationsRepository.class);
		when(identificationsRepository.findAll()).thenReturn(Arrays.asList(
				new IdentificationDto(1, "1", 0, 30, 1),
				new IdentificationDto(2, "2", 0, 30, 2))
		);

		IdentificationsOrderingServiceImpl service = new IdentificationsOrderingServiceImpl(
				companiesRepository, identificationsRepository);
		List<IdentificationDto> actual = service.getOrderedIdentifications();
		assertEquals(1, actual.get(0).getId());
		assertEquals(2, actual.get(1).getId());
	}

	@Test
	public void example4()  {
		CompaniesRepository companiesRepository = mock(CompaniesRepository.class);
		when(companiesRepository.find(1)).thenReturn(new CompanyDto(1, "1", 60, 0.9f, 0.95f));
		when(companiesRepository.find(2)).thenReturn(new CompanyDto(2, "2", 120, 0.8f, 0.80f));

		IdentificationsRepository identificationsRepository = mock(IdentificationsRepository.class);
		when(identificationsRepository.findAll()).thenReturn(Arrays.asList(
				new IdentificationDto(1, "1", 0, 45, 1),
				new IdentificationDto(2, "2", 0, 30, 2))
		);

		IdentificationsOrderingServiceImpl service = new IdentificationsOrderingServiceImpl(
				companiesRepository, identificationsRepository);
		List<IdentificationDto> actual = service.getOrderedIdentifications();
		assertEquals(2, actual.get(0).getId());
		assertEquals(1, actual.get(1).getId());
	}
}