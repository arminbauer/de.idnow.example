import identification.Company;
import identification.Identification;
import identification.IdentificationPrioritizer;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class IdentificationTest {

    private IdentificationPrioritizer identificationPrioritizer = new IdentificationPrioritizer();
    private final Long now = ZonedDateTime.now().toEpochSecond();
    private static int nextCompanyId = 1;

    @Test
    public void noIdentifications_emptyResult() {
        assertThat(identificationPrioritizer.prioritize()).isEmpty();
    }

    @Test
    public void singleIdentification_isReturned() {
        Identification identification = Identification.builder().build();
        identificationPrioritizer.add(identification);
        assertThat(identificationPrioritizer.prioritize()).containsExactly(identification);
    }

    @Test
    public void forSameCompany_longerWaitingTimeHasHigherPrio() {
        Company company = defaultCompany().build();
        identificationPrioritizer.add(company);
        Identification shorterWaiting = Identification.builder().id(1).companyId(company.getId()).time(now - 5).waitingTime(5).build();
        identificationPrioritizer.add(shorterWaiting);
        Identification longerWaiting = Identification.builder().id(2).companyId(company.getId()).time(now - 10).waitingTime(10).build();
        identificationPrioritizer.add(longerWaiting);
        assertThat(identificationPrioritizer.prioritize()).containsExactly(longerWaiting, shorterWaiting);
    }

    @Test
    public void forTwoCompaniesWithDifferentSlas_lowerSlaPercentageHasHigherPrio() {
        Company c1 = defaultCompany().slaTime(60).slaPercentage(new BigDecimal("0.9")).build();
        Company c2 = defaultCompany().slaTime(120).slaPercentage(new BigDecimal("0.8")).build();
        identificationPrioritizer.add(c1);
        identificationPrioritizer.add(c2);
        Identification c1Identification = Identification.builder().id(1).companyId(c1.getId()).time(now - 30).waitingTime(30).build();
        identificationPrioritizer.add(c1Identification);
        Identification c2Identification = Identification.builder().id(2).companyId(c2.getId()).time(now - 30).waitingTime(30).build();
        identificationPrioritizer.add(c2Identification);
        assertThat(identificationPrioritizer.prioritize()).containsExactly(c1Identification, c2Identification);
    }

    @Test
    public void forTwoCompaniesWithEqualSlaPercentages_lowerSlaTimeHasHigherPrio() {
        Company c1 = defaultCompany().slaTime(60).slaPercentage(new BigDecimal("0.9")).build();
        Company c2 = defaultCompany().slaTime(120).slaPercentage(new BigDecimal("0.9")).build();
        identificationPrioritizer.add(c1);
        identificationPrioritizer.add(c2);
        Identification c1Identification = Identification.builder().id(1).companyId(c1.getId()).time(now - 30).waitingTime(30).build();
        identificationPrioritizer.add(c1Identification);
        Identification c2Identification = Identification.builder().id(2).companyId(c2.getId()).time(now - 30).waitingTime(30).build();
        identificationPrioritizer.add(c2Identification);
        assertThat(identificationPrioritizer.prioritize()).containsExactly(c1Identification, c2Identification);
    }

    @Test
    public void forTwoCompaniesWithSameSlas_lowerCurrentSlaHasHigherPrio() {
        Company c1 = defaultCompany().currentSla(new BigDecimal("0.95")).build();
        Company c2 = defaultCompany().currentSla(new BigDecimal("0.9")).build();
        identificationPrioritizer.add(c1);
        identificationPrioritizer.add(c2);
        Identification c1Identification = Identification.builder().id(1).companyId(c1.getId()).time(now - 30).waitingTime(30).build();
        identificationPrioritizer.add(c1Identification);
        Identification c2Identification = Identification.builder().id(2).companyId(c2.getId()).time(now - 30).waitingTime(30).build();
        identificationPrioritizer.add(c2Identification);
        assertThat(identificationPrioritizer.prioritize()).containsExactly(c2Identification, c1Identification);
    }

    @Test
    public void overdueIdentifications_alwaysGoLast() {
        Company company = defaultCompany().slaTime(30).build();
        identificationPrioritizer.add(company);
        Identification inSla = Identification.builder().id(1).companyId(company.getId()).time(now - 10).waitingTime(10).build();
        identificationPrioritizer.add(inSla);
        Identification overdue = Identification.builder().id(2).companyId(company.getId()).time(now - 60).waitingTime(60).build();
        identificationPrioritizer.add(overdue);
        assertThat(identificationPrioritizer.prioritize()).containsExactly(inSla, overdue);
    }

    private Company.CompanyBuilder defaultCompany() {
        return Company.builder().id(nextCompanyId++).slaTime(60).slaPercentage(new BigDecimal("0.9")).currentSla(new BigDecimal("0.95"));
    }
}
