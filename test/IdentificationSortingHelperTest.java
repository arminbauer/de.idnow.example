import helpers.IdentificationSortingHelper;
import models.Identification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
public class IdentificationSortingHelperTest {
  private IdentificationSortingHelper helper;

  @Before
  public void setUp() {
    helper = new IdentificationSortingHelper();
  }

  @Test
  public void testSortBySlaReturnsEmptyListOnEmptyInput() {
    Assert.assertEquals(helper.sortBySla(Collections.emptyList()), Collections.emptyList());
  }

  @Test
  public void testSortBySlaDoesNotChangeOneElementList() {
    final Identification o = new Identification();
    Assert.assertEquals(helper.sortBySla(Collections.singletonList(o)), Collections.singletonList(o));
  }
}
