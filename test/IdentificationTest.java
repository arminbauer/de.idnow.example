import identification.Company;
import identification.Identification;
import identification.IdentificationPrioritizer;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class IdentificationTest {

    private IdentificationPrioritizer identificationPrioritizer = new IdentificationPrioritizer();

    @Test
    public void emptyResultWhenNoIdentifications() {
        assertThat(identificationPrioritizer.prioritize()).isEmpty();
    }

    @Test
    public void singleIdentificationIsReturned() {
        Identification identification = Identification.builder().build();
        identificationPrioritizer.add(identification);
        assertThat(identificationPrioritizer.prioritize()).containsExactly(identification);
    }

    @Test
    public void forSameCompanyLongerWaitingTimeHasHigherPrio() {
        Company company = Company.builder().id(1).build();
        ZonedDateTime now = ZonedDateTime.now();
        Identification shorterWaiting = Identification.builder().id(1).companyId(company.getId()).time(now.minusSeconds(5).toEpochSecond()).build();
        identificationPrioritizer.add(shorterWaiting);
        Identification longerWaiting = Identification.builder().id(2).companyId(company.getId()).time(now.minusSeconds(10).toEpochSecond()).build();
        identificationPrioritizer.add(longerWaiting);
        assertThat(identificationPrioritizer.prioritize()).containsExactly(longerWaiting, shorterWaiting);
    }

    @Test
    public void forTwoCompaniesWithSameSlas_lowerCurrentSlaHasHigherPrio() {
        Company c1 = Company.builder().id(1).slaTime(60).slaPercentage(new BigDecimal("0.9")).currentSla(new BigDecimal("0.95")).build();
        Company c2 = Company.builder().id(2).slaTime(60).slaPercentage(new BigDecimal("0.9")).currentSla(new BigDecimal("0.9")).build();
        identificationPrioritizer.add(c1);
        identificationPrioritizer.add(c2);
        ZonedDateTime now = ZonedDateTime.now();
        Identification c1Identification = Identification.builder().id(1).companyId(c1.getId()).time(now.minusSeconds(30).toEpochSecond()).build();
        identificationPrioritizer.add(c1Identification);
        Identification c2Identification = Identification.builder().id(2).companyId(c2.getId()).time(now.minusSeconds(30).toEpochSecond()).build();
        identificationPrioritizer.add(c2Identification);
        assertThat(identificationPrioritizer.prioritize()).containsExactly(c2Identification, c1Identification);
    }
}
