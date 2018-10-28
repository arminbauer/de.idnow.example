import com.google.inject.Guice;
import com.google.inject.Injector;
import exception.NoCompanyAssociatedException;
import model.Company;
import model.Identification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.IdentificationProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class IdentificationProcessorTest {
    private Injector injector;
    private IdentificationProcessor identificationProcessor;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector();
        identificationProcessor = injector.getInstance(IdentificationProcessor.class);
    }

    @After
    public void tearDown() throws Exception {
        injector = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCompanyFailsWhenNoId() {
        Company company = new Company();
        identificationProcessor.updateCompanyInformation(company);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCompanyFailsWhenNoSlaPercentage() {
        Company company = new Company();
        company.setId(123);
        identificationProcessor.updateCompanyInformation(company);
    }

    @Test
    public void testAddCompanySuccessful() {
        addCompanyToProcessor(132);
    }

    @Test
    public void testAddCompanySuccessfulForManyThreads() throws Exception {
        Company company = new Company();
        company.setSlaPercentage(0.9f);

        int threadCount = 20;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        List<Callable<Void>> callables = new ArrayList<>(threadCount);
        IntStream.range(0, threadCount).forEach(i -> callables.add(() -> {
            company.setId(i % 2);
            identificationProcessor.updateCompanyInformation(company);
            return null;
        } ));
        List<Future<Void>> futureList = executorService.invokeAll(callables);

        boolean allDone = false;
        while (!allDone) {
            allDone = true;
            for(Future<?> future : futureList){
                allDone &= future.isDone(); // check if future is done
            }
        }

    }


    @Test(expected = NoCompanyAssociatedException.class)
    public void testAddIdentificationFailsWhenNoCompanyId() throws Exception {
        Identification identification = new Identification();
        identificationProcessor.addNewIdentification(identification);
    }

    @Test(expected = NoCompanyAssociatedException.class)
    public void testAddIdentificationFailsWhenNoCompanyPresent() throws Exception {
        Identification identification = new Identification();
        identification.setCompanyId(123);
        identificationProcessor.addNewIdentification(identification);
    }

    @Test
    public void testAddIdentificationSuccessful() throws Exception {
        int companyId = addCompanyToProcessor(123);

        Identification identification = new Identification();
        identification.setCompanyId(companyId);
        identificationProcessor.addNewIdentification(identification);
    }

    @Test
    public void testAddIdentificationSuccessfulForManyThreads() throws Exception {
        int companyId = addCompanyToProcessor(123);

        int threadCount = 20;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        List<Callable<Void>> callables = new ArrayList<>(threadCount);
        IntStream.range(0, threadCount).forEach(i -> callables.add(() -> {
            Identification identification = new Identification();
            identification.setCompanyId(companyId);
            identification.setWaitingTime(i);
            identificationProcessor.addNewIdentification(identification);
            return null;
        } ));
        List<Future<Void>> futureList = executorService.invokeAll(callables);

        boolean allDone = false;
        while (!allDone) {
            allDone = true;
            for(Future<?> future : futureList){
                allDone &= future.isDone(); // check if future is done
            }
        }
    }

    @Test
    public void testIdentificationFromOneCompanyOrderedInTimeToFailOrder() {
        int companyId = addCompanyToProcessor(123, 30);

        int identificationCount = 4;
        IntStream.range(0, identificationCount).forEach(i -> {
            Identification identification = new Identification();
            identification.setId(i);
            identification.setCompanyId(companyId);
            identification.setWaitingTime(i * 5);
            try {
                identificationProcessor.addNewIdentification(identification);
            } catch (NoCompanyAssociatedException e) {
                fail();
            }
        });

        List<Identification> identifications = identificationProcessor.getOrderedPendingIdentifications();
        assertEquals(identificationCount, identifications.size());
        IntStream.range(0, identificationCount).forEach(i -> {
            Identification identification = identifications.get(i);
            assertEquals(identificationCount - 1 - i, identification.getId());
        });
    }

    @Test
    public void testExpiredIdentificationIsPutLast() throws Exception {
        int companyId = addCompanyToProcessor(123, 30);

        Identification first = new Identification();
        first.setCompanyId(companyId);
        first.setWaitingTime(10);
        identificationProcessor.addNewIdentification(first);

        Identification second = new Identification();
        second.setCompanyId(companyId);
        second.setWaitingTime(20);
        identificationProcessor.addNewIdentification(second);

        Identification expired = new Identification();
        expired.setCompanyId(companyId);
        expired.setWaitingTime(40);
        identificationProcessor.addNewIdentification(expired);

        List<Identification> identifications = identificationProcessor.getOrderedPendingIdentifications();
        assertEquals(3, identifications.size());
        assertEquals(identifications.get(0).getWaitingTime(), second.getWaitingTime());
        assertEquals(identifications.get(1).getWaitingTime(), first.getWaitingTime());
        assertEquals(identifications.get(2).getWaitingTime(), expired.getWaitingTime());
    }

    @Test
    public void testIdentificationsOfUnderPerformingCompanyGoFirst() throws Exception {
        Company company1 = new Company();
        company1.setId(1);
        company1.setSlaPercentage(0.8f);
        company1.setCurrentSlaPercentage(0.9f);
        company1.setSlaTime(40);
        identificationProcessor.updateCompanyInformation(company1);

        Company company2 = new Company();
        company2.setId(2);
        company2.setSlaPercentage(0.9f);
        company2.setCurrentSlaPercentage(0.8f);
        company2.setSlaTime(40);
        identificationProcessor.updateCompanyInformation(company2);

        Identification firstFrom1 = new Identification();
        firstFrom1.setId(1);
        firstFrom1.setCompanyId(company1.getId());
        firstFrom1.setWaitingTime(10);
        identificationProcessor.addNewIdentification(firstFrom1);

        Identification secondFrom1 = new Identification();
        secondFrom1.setId(2);
        secondFrom1.setCompanyId(company1.getId());
        secondFrom1.setWaitingTime(39);
        identificationProcessor.addNewIdentification(secondFrom1);

        Identification firstFrom2 = new Identification();
        firstFrom2.setId(3);
        firstFrom2.setCompanyId(company2.getId());
        firstFrom2.setWaitingTime(30);
        identificationProcessor.addNewIdentification(firstFrom2);

        Identification expiredFrom2 = new Identification();
        expiredFrom2.setId(4);
        expiredFrom2.setCompanyId(company2.getId());
        expiredFrom2.setWaitingTime(45);
        identificationProcessor.addNewIdentification(expiredFrom2);

        List<Identification> identifications = identificationProcessor.getOrderedPendingIdentifications();
        assertEquals(4, identifications.size());
        assertEquals(identifications.get(0).getId(), firstFrom2.getId()); // second company has worse performance
        assertEquals(identifications.get(1).getId(), secondFrom1.getId()); // ordered by time to fail
        assertEquals(identifications.get(2).getId(), firstFrom1.getId()); //
        assertEquals(identifications.get(3).getId(), expiredFrom2.getId()); //goes last
    }

    @Test
    public void testCriticalIdentificationsAreFirstBetweenTwoOverPerformingCompanies() throws Exception {
        Company company1 = new Company();
        company1.setId(1);
        company1.setSlaPercentage(0.8f);
        company1.setCurrentSlaPercentage(0.9f);
        company1.setSlaTime(40);
        identificationProcessor.updateCompanyInformation(company1);

        Company company2 = new Company();
        company2.setId(2);
        company2.setSlaPercentage(0.8f);
        company2.setCurrentSlaPercentage(0.85f);
        company2.setSlaTime(40);
        identificationProcessor.updateCompanyInformation(company2);

        Identification firstFrom1 = new Identification();
        firstFrom1.setId(1);
        firstFrom1.setCompanyId(company1.getId());
        firstFrom1.setWaitingTime(10);
        identificationProcessor.addNewIdentification(firstFrom1);

        Identification secondFrom1 = new Identification();
        secondFrom1.setId(2);
        secondFrom1.setCompanyId(company1.getId());
        secondFrom1.setWaitingTime(39);
        identificationProcessor.addNewIdentification(secondFrom1);

        Identification firstFrom2 = new Identification();
        firstFrom2.setId(3);
        firstFrom2.setCompanyId(company2.getId());
        firstFrom2.setWaitingTime(30);
        identificationProcessor.addNewIdentification(firstFrom2);

        Identification expiredFrom2 = new Identification();
        expiredFrom2.setId(4);
        expiredFrom2.setCompanyId(company2.getId());
        expiredFrom2.setWaitingTime(45);
        identificationProcessor.addNewIdentification(expiredFrom2);

        List<Identification> identifications = identificationProcessor.getOrderedPendingIdentifications();
        assertEquals(4, identifications.size());
        assertEquals(identifications.get(0).getId(), secondFrom1.getId()); // critical time to fail
        assertEquals(identifications.get(1).getId(), firstFrom2.getId()); // second company has worse performance
        assertEquals(identifications.get(2).getId(), firstFrom1.getId()); // first company has better performance
        assertEquals(identifications.get(3).getId(), expiredFrom2.getId()); // expired goes last
    }

    @Test
    public void testCriticalIdentificationsAreFirstBetweenTwoUnderPerformingCompanies() throws Exception {
        Company company1 = new Company();
        company1.setId(1);
        company1.setSlaPercentage(0.8f);
        company1.setCurrentSlaPercentage(0.8f);
        company1.setSlaTime(10);
        identificationProcessor.updateCompanyInformation(company1);

        Company company2 = new Company();
        company2.setId(2);
        company2.setSlaPercentage(0.8f);
        company2.setCurrentSlaPercentage(0.75f);
        company2.setSlaTime(40);
        identificationProcessor.updateCompanyInformation(company2);

        Identification firstFrom1 = new Identification();
        firstFrom1.setId(1);
        firstFrom1.setCompanyId(company1.getId());
        firstFrom1.setWaitingTime(10);
        identificationProcessor.addNewIdentification(firstFrom1);

        Identification firstFrom2 = new Identification();
        firstFrom2.setId(3);
        firstFrom2.setCompanyId(company2.getId());
        firstFrom2.setWaitingTime(30);
        identificationProcessor.addNewIdentification(firstFrom2);

        List<Identification> identifications = identificationProcessor.getOrderedPendingIdentifications();
        assertEquals(2, identifications.size());
        assertEquals(identifications.get(0).getId(), firstFrom1.getId()); // critical time for an identification
        assertEquals(identifications.get(1).getId(), firstFrom2.getId()); // second company has worse performance, but not critical time
    }

    @Test
    public void testIdentificationsAreSortedByCompanySlaPerformanceWhenNoCritical() throws Exception {
        Company company1 = new Company();
        company1.setId(1);
        company1.setSlaPercentage(0.8f);
        company1.setCurrentSlaPercentage(0.8f);
        company1.setSlaTime(50);
        identificationProcessor.updateCompanyInformation(company1);

        Company company2 = new Company();
        company2.setId(2);
        company2.setSlaPercentage(0.8f);
        company2.setCurrentSlaPercentage(0.75f);
        company2.setSlaTime(60);
        identificationProcessor.updateCompanyInformation(company2);

        Identification firstFrom1 = new Identification();
        firstFrom1.setId(1);
        firstFrom1.setCompanyId(company1.getId());
        firstFrom1.setWaitingTime(10);
        identificationProcessor.addNewIdentification(firstFrom1);

        Identification secondFrom1 = new Identification();
        secondFrom1.setId(2);
        secondFrom1.setCompanyId(company1.getId());
        secondFrom1.setWaitingTime(30);
        identificationProcessor.addNewIdentification(secondFrom1);

        Identification firstFrom2 = new Identification();
        firstFrom2.setId(3);
        firstFrom2.setCompanyId(company2.getId());
        firstFrom2.setWaitingTime(30);
        identificationProcessor.addNewIdentification(firstFrom2);

        Identification secondFrom2 = new Identification();
        secondFrom2.setId(4);
        secondFrom2.setCompanyId(company2.getId());
        secondFrom2.setWaitingTime(40);
        identificationProcessor.addNewIdentification(secondFrom2);


        List<Identification> identifications = identificationProcessor.getOrderedPendingIdentifications();
        assertEquals(4, identifications.size());
        assertEquals(identifications.get(0).getId(), secondFrom2.getId()); // 2nd company has worse performance, 2nd id waits more
        assertEquals(identifications.get(1).getId(), firstFrom2.getId()); // 2nd company has worse performance, 1st id waits less
        assertEquals(identifications.get(2).getId(), secondFrom1.getId()); // 1st company has better performance, 2nd id waits more
        assertEquals(identifications.get(3).getId(), firstFrom1.getId()); // 1st company has better performance, 1st id waits less
    }

    private int addCompanyToProcessor(int companyId) {
        return addCompanyToProcessor(companyId, 40);
    }

    private int addCompanyToProcessor(int companyId, int slaTime) {
        Company company = new Company();
        company.setId(companyId);
        company.setSlaPercentage(0.9f);
        company.setCurrentSlaPercentage(0.8f);
        company.setSlaTime(slaTime);
        identificationProcessor.updateCompanyInformation(company);
        return companyId;
    }
}
