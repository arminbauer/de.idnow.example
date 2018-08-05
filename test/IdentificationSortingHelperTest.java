import helpers.IdentificationSortingHelper;
import models.Company;
import models.Identification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
public class IdentificationSortingHelperTest {
  private static final AtomicLong IDENT_ID = new AtomicLong(1);
  private static final AtomicLong COMPANY_ID = new AtomicLong(1);
  private IdentificationSortingHelper helper;

  @Before
  public void setUp() {
    helper = new IdentificationSortingHelper();
  }

  @Test
  public void testSortBySlaReturnsEmptyListOnEmptyInput() {
    Assert.assertEquals(Collections.emptyList(), helper.sortBySla(Collections.emptyList()));
  }

  @Test
  public void testSortBySlaDoesNotChangeOneElementList() {
    final Identification o = TestHelper.buildDefaultIdentification(TestHelper.buildDefaultCompany(-100L));
    Assert.assertEquals(Collections.singletonList(o), helper.sortBySla(Collections.singletonList(o)));
  }

  @Test
  public void testSortBySlaSortsSmallestWaitingTimesFirst() {
    final Company company1 = company1();
    final Identification identification1 = identification(company1, 30);
    final Identification identification2 = identification(company1, 45);
    Assert.assertEquals(Arrays.asList(identification2, identification1), helper.sortBySla(Arrays.asList(identification1, identification2)));
  }

  @Test
  public void testSortBySlaSortsLowerCurrentSlaFirstForSameWaitingTimes() {
    final Company company1 = company1();
    final Company company2 = company2();
    final Identification identification1 = identification(company1, 30);
    final Identification identification2 = identification(company2, 30);
    Assert.assertEquals(Arrays.asList(identification2, identification1), helper.sortBySla(Arrays.asList(identification1, identification2)));
  }

  @Test
  public void testSortBySlaSortsLowerSlaTimeFirstForSameWaitingTimesAndCurrentSlaPercentage() {
    final Company company1 = company1();
    final Company company2 = company3();
    final Identification identification1 = identification(company1, 30);
    final Identification identification2 = identification(company2, 30);
    Assert.assertEquals(Arrays.asList(identification1, identification2), helper.sortBySla(Arrays.asList(identification1, identification2)));
  }

  @Test
  public void testSortBySlaSortsDangerousSlaFirstForSameWaitingTimesAndCurrentSlaPercentage() {
    final Company company1 = company1();
    final Company company2 = company4();
    final Identification identification1 = identification(company1, 45);
    final Identification identification2 = identification(company2, 30);
    Assert.assertEquals(Arrays.asList(identification1, identification2), helper.sortBySla(Arrays.asList(identification1, identification2)));
  }

  @Test
  public void testSortMultipleItemsReturnsCorrectResult() {
    final Company company1 = company1();
    final Company company2 = company4();
    final Company company3 = company4();
    final Company company4 = company4();
    final Identification identification1 = identification(company1, 30);
    final Identification identification2 = identification(company1, 45);
    final Identification identification3 = identification(company2, 30);
    final Identification identification4 = identification(company3, 30);
    final Identification identification5 = identification(company4, 30);
    final Identification identification6 = identification(company1, 45);
    final List<Identification> actual = helper.sortBySla(Arrays.asList(identification1,
                                                                       identification2,
                                                                       identification3,
                                                                       identification4,
                                                                       identification5,
                                                                       identification6));
    Assert.assertEquals(Arrays.asList(2L, 6L, 3L, 4L, 5L, 1L), actual.stream().map(Identification::getId).collect(Collectors.toList()));
  }

  @Nonnull
  private Identification identification(final Company company1, final int waitingTime) {
    return TestHelper.buildIdentification(IDENT_ID.getAndIncrement(), company1, "default", LocalDateTime.now().minus(waitingTime, ChronoUnit.SECONDS));
  }

  @Nonnull
  private Company company1() {
    return TestHelper.buildCompany(COMPANY_ID.getAndIncrement(), "Company1", 60, 0.9f, 0.95f);
  }

  @Nonnull
  private Company company2() {
    return TestHelper.buildCompany(COMPANY_ID.getAndIncrement(), "Company2", 60, 0.9f, 0.9f);
  }

  @Nonnull
  private Company company3() {
    return TestHelper.buildCompany(COMPANY_ID.getAndIncrement(), "Company3", 120, 0.8f, 0.95f);
  }

  @Nonnull
  private Company company4() {
    return TestHelper.buildCompany(COMPANY_ID.getAndIncrement(), "Company4", 120, 0.8f, 0.8f);
  }
}
