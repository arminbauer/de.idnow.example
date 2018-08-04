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
import java.util.Random;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
public class IdentificationSortingHelperTest {
  private static final Random RANDOM = new Random();
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
    final List<Identification> sorted = helper.sortBySla(Arrays.asList(identification1, identification2));
    final List<Identification> expected = Arrays.asList(identification2, identification1);
    Assert.assertEquals(expected, sorted);
  }

  @Nonnull
  private Identification identification(final Company company1, final int waitingTime) {
    return TestHelper.buildIdentification(RANDOM.nextLong(), company1, "default", LocalDateTime.now().minus(waitingTime, ChronoUnit.SECONDS));
  }

  @Nonnull
  private Company company1() {
    return TestHelper.buildCompany(RANDOM.nextLong(), "Company1", 60, 0.9f, 0.95f);
  }

  @Nonnull
  private Company company2() {
    return TestHelper.buildCompany(RANDOM.nextLong(), "Company1", 60, 0.9f, 0.9f);
  }
}
