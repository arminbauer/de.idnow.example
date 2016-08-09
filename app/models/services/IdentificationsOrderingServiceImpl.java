package models.services;

import models.repositories.CompaniesRepository;
import models.repositories.IdentificationsRepository;
import models.dtos.CompanyDto;
import models.dtos.IdentificationDto;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class IdentificationsOrderingServiceImpl implements IdentificationsOrderingService {
	private final CompaniesRepository companiesRepository;
	private final IdentificationsRepository identificationsRepository;

	@Inject
	public IdentificationsOrderingServiceImpl(
			CompaniesRepository companiesRepository,
			IdentificationsRepository identificationsRepository) {

		this.companiesRepository = companiesRepository;
		this.identificationsRepository = identificationsRepository;
	}

	@Override
	public List<IdentificationDto> getOrderedIdentifications() {
		List<IdentificationDto> identifications = identificationsRepository.findAll();

		HashMap<Long, CompanyDto> companies = new HashMap<>();
		HashMap<CompaniesPair, Integer> companiesComparisonResults = new HashMap<>();

		return identifications
				.stream()
				.sorted((i1, i2) -> compareIdentifications(companies, companiesComparisonResults, i1, i2))
				.collect(Collectors.toList());
	}

	private int compareIdentifications(
			HashMap<Long, CompanyDto> companies,
			HashMap<CompaniesPair, Integer> companiesComparisonResults,
			IdentificationDto i1,
			IdentificationDto i2) {

		CompaniesPair companiesPair = new CompaniesPair(i1.getCompanyId(), i2.getCompanyId());
		int companyComparisonResult = companiesComparisonResults.computeIfAbsent(
				companiesPair, p -> compareCompanies(companies, p)
		);

		if (companyComparisonResult == 0) {
			// same company
			return -Integer.compare(i1.getWaitingTime(), i2.getWaitingTime());
		}

		return companyComparisonResult;
	}

	private int compareCompanies(HashMap<Long, CompanyDto> companies, CompaniesPair pair) {
		CompanyDto c1 = companies.computeIfAbsent(pair.c1, companiesRepository::find);
		CompanyDto c2 = companies.computeIfAbsent(pair.c2, companiesRepository::find);

		float slaResource1 = c1.getCurrentSlaPercentage() - c1.getSlaPercentage();
		float slaResource2 = c2.getCurrentSlaPercentage() - c2.getSlaPercentage();
		int slaResourcesComparisonResult = Float.compare(slaResource1, slaResource2);
		if (slaResourcesComparisonResult == 0) {
			return Integer.compare(c1.getSlaTime(), c2.getSlaTime());
		}

		return slaResourcesComparisonResult;
	}

	private static class CompaniesPair {
		private final long c1;
		private final long c2;

		private CompaniesPair(long c1, long c2) {
			this.c1 = c1;
			this.c2 = c2;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			CompaniesPair that = (CompaniesPair) o;

			return c1 == that.c1 && c2 == that.c2;
		}

		@Override
		public int hashCode() {
			int result = Long.hashCode(c1);
			result = 31 * result + Long.hashCode(c2);

			return result;
		}
	}
}
