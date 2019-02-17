package service;

import models.CompanyEntity;
import models.IdentificationEntity;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Identification comparator tests
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class IdentificationComparatorTest {

    @Test
    public void testTheSameCompany() {
        CompanyEntity company = CompanyEntity.builder().id(1L).name("Company 1")
                .slaTime(60L).slaPercentage(90F).currentSlaPercentage(95F).build();
        IdentificationEntity identification1 = IdentificationEntity.builder()
                .id(1L).waitingTime(30L).companyEntity(company).build();
        IdentificationEntity identification2 = IdentificationEntity.builder()
                .id(2L).waitingTime(45L).companyEntity(company).build();
        IdentificationComparator identificationComparator = new IdentificationComparator();
        List<IdentificationEntity> identificationList = Arrays.asList(identification1, identification2);
        identificationList.sort(identificationComparator);
        assertEquals(new Long(2L), identificationList.get(0).getId());
    }

    @Test
    public void testIdenticalCompanyValues() {
        CompanyEntity company = CompanyEntity.builder().id(1L).name("Company 1")
                .slaTime(60L).slaPercentage(90F).currentSlaPercentage(95F).build();
        CompanyEntity company2 = CompanyEntity.builder().id(2L).name("Company 2")
                .slaTime(60L).slaPercentage(90F).currentSlaPercentage(95F).build();
        IdentificationEntity identification1 = IdentificationEntity.builder()
                .id(1L).waitingTime(30L).companyEntity(company).build();
        IdentificationEntity identification2 = IdentificationEntity.builder()
                .id(2L).waitingTime(45L).companyEntity(company2).build();
        IdentificationComparator identificationComparator = new IdentificationComparator();
        List<IdentificationEntity> identificationList = Arrays.asList(identification1, identification2);
        assertEquals(new Long(1L), identificationList.get(0).getId());
        identificationList.sort(identificationComparator);
        assertEquals(new Long(2L), identificationList.get(0).getId());
    }

    @Test
    public void testSecondCurrentSlaLower() {
        CompanyEntity company = CompanyEntity.builder().id(1L).name("Company 1")
                .slaTime(60L).slaPercentage(90F).currentSlaPercentage(95F).build();
        CompanyEntity company2 = CompanyEntity.builder().id(2L).name("Company 2")
                .slaTime(60L).slaPercentage(90F).currentSlaPercentage(90F).build();
        IdentificationEntity identification1 = IdentificationEntity.builder()
                .id(1L).waitingTime(60L).companyEntity(company).build();
        IdentificationEntity identification2 = IdentificationEntity.builder()
                .id(2L).waitingTime(60L).companyEntity(company2).build();
        IdentificationComparator identificationComparator = new IdentificationComparator();
        List<IdentificationEntity> identificationList = Arrays.asList(identification1, identification2);
        identificationList.sort(identificationComparator);
        assertEquals(new Long(2L), identificationList.get(0).getId());
    }

    @Test
    public void testMultipleIdentifications() {
        CompanyEntity company = CompanyEntity.builder().id(1L).name("Company 1")
                .slaTime(60L).slaPercentage(90F).currentSlaPercentage(95F).build();
        CompanyEntity company2 = CompanyEntity.builder().id(2L).name("Company 2")
                .slaTime(120L).slaPercentage(80F).currentSlaPercentage(80F).build();
        CompanyEntity company3 = CompanyEntity.builder().id(3L).name("Company 3")
                .slaTime(60L).slaPercentage(90F).currentSlaPercentage(95F).build();
        CompanyEntity company4 = CompanyEntity.builder().id(4L).name("Company 4")
                .slaTime(60L).slaPercentage(90F).currentSlaPercentage(90F).build();

        IdentificationEntity identification1 = IdentificationEntity.builder()
                .id(1L).waitingTime(45L).companyEntity(company).build();
        IdentificationEntity identification2 = IdentificationEntity.builder()
                .id(2L).waitingTime(60L).companyEntity(company2).build();
        IdentificationEntity identification3 = IdentificationEntity.builder()
                .id(3L).waitingTime(45L).companyEntity(company2).build();
        IdentificationEntity identification4 = IdentificationEntity.builder()
                .id(4L).waitingTime(30L).companyEntity(company3).build();
        IdentificationEntity identification5 = IdentificationEntity.builder()
                .id(5L).waitingTime(45L).companyEntity(company4).build();

        IdentificationComparator identificationComparator = new IdentificationComparator();
        List<IdentificationEntity> identificationList = Arrays.asList(identification1, identification2,
                identification3, identification4, identification5);
        identificationList.sort(identificationComparator);
        assertEquals("2,3,5,1,4", getQueueString(identificationList));
    }

    @Test
    public void testMultipleIdentificationsWithTwoWeights() {
        CompanyEntity company = CompanyEntity.builder().id(1L).name("Company 1")
                .slaTime(60L).slaPercentage(90F).currentSlaPercentage(95F).build();
        CompanyEntity company2 = CompanyEntity.builder().id(2L).name("Company 2")
                .slaTime(120L).slaPercentage(80F).currentSlaPercentage(80F).build();
        CompanyEntity company3 = CompanyEntity.builder().id(3L).name("Company 3")
                .slaTime(60L).slaPercentage(90F).currentSlaPercentage(95F).build();
        CompanyEntity company4 = CompanyEntity.builder().id(4L).name("Company 4")
                .slaTime(60L).slaPercentage(90F).currentSlaPercentage(90F).build();

        IdentificationEntity identification1 = IdentificationEntity.builder()
                .id(1L).waitingTime(45L).companyEntity(company).build();
        IdentificationEntity identification2 = IdentificationEntity.builder()
                .id(2L).waitingTime(60L).companyEntity(company2).build();
        IdentificationEntity identification3 = IdentificationEntity.builder()
                .id(3L).waitingTime(45L).companyEntity(company2).build();
        IdentificationEntity identification4 = IdentificationEntity.builder()
                .id(4L).waitingTime(300L).companyEntity(company3).build();
        IdentificationEntity identification5 = IdentificationEntity.builder()
                .id(5L).waitingTime(45L).companyEntity(company4).build();

        IdentificationComparator identificationComparator = new IdentificationComparator(0.95f, 0.03f, 0.02f);
        List<IdentificationEntity> identificationList = Arrays.asList(identification1, identification2,
                identification3, identification4, identification5);
        identificationList.sort(identificationComparator);
        assertEquals("4,5,2,3,1", getQueueString(identificationList));
    }

    @Test
    public void testMultipleIdentificationsWithThreeWeights() {
        CompanyEntity company = CompanyEntity.builder().id(1L).name("Company 1")
                .slaTime(60L).slaPercentage(90F).currentSlaPercentage(95F).build();
        CompanyEntity company2 = CompanyEntity.builder().id(2L).name("Company 2")
                .slaTime(120L).slaPercentage(20F).currentSlaPercentage(20F).build();
        CompanyEntity company3 = CompanyEntity.builder().id(3L).name("Company 3")
                .slaTime(60L).slaPercentage(90F).currentSlaPercentage(95F).build();
        CompanyEntity company4 = CompanyEntity.builder().id(4L).name("Company 4")
                .slaTime(60L).slaPercentage(90F).currentSlaPercentage(90F).build();

        IdentificationEntity identification1 = IdentificationEntity.builder()
                .id(1L).waitingTime(45L).companyEntity(company).build();
        IdentificationEntity identification2 = IdentificationEntity.builder()
                .id(2L).waitingTime(60L).companyEntity(company2).build();
        IdentificationEntity identification3 = IdentificationEntity.builder()
                .id(3L).waitingTime(45L).companyEntity(company2).build();
        IdentificationEntity identification4 = IdentificationEntity.builder()
                .id(4L).waitingTime(300L).companyEntity(company3).build();
        IdentificationEntity identification5 = IdentificationEntity.builder()
                .id(5L).waitingTime(45L).companyEntity(company4).build();

        IdentificationComparator identificationComparator = new IdentificationComparator(0.53f, 0.45f, 0.02f);
        List<IdentificationEntity> identificationList = Arrays.asList(identification1, identification2,
                identification3, identification4, identification5);
        identificationList.sort(identificationComparator);
        assertEquals("2,3,4,5,1", getQueueString(identificationList));
    }

    private String getQueueString(List<IdentificationEntity> identifications) {
        return identifications.stream().map(identification -> identification.getId().toString())
                .collect(Collectors.joining(","));
    }


}
