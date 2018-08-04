package helpers;

import models.Identification;

import javax.inject.Singleton;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
@Singleton
public class IdentificationSortingHelper {
  public List<Identification> sortBySla(final List<Identification> identifications) {
    final Comparator<Identification> waitingTimeComparator = (o1, o2) -> Comparator.comparingLong(Identification::waitingTime).compare(o2, o1);
    final Comparator<Identification> slaPercentageComparator = (o1, o2) -> Comparator.comparingDouble(this::getPercentageRatio).compare(o1, o2);
    return identifications.stream().sorted(slaPercentageComparator).sorted(waitingTimeComparator).collect(Collectors.toList());
  }

  private float getPercentageRatio(final Identification c) {
    return c.getCompany().getCurrentSlaPercentage() / c.getCompany().getSlaPercentage();
  }
}
