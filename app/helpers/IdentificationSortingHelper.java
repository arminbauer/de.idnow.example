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
@SuppressWarnings("FieldCanBeLocal")
@Singleton
public class IdentificationSortingHelper {
  private final double DANGER_ZONE_SLA_DELTA = 0.05;
  private final Comparator<Identification> DANGEROUS_ZONE_COMPARATOR = dangerousZoneComparator();

  public List<Identification> sortBySla(final List<Identification> identifications) {
    final Comparator<Identification> waitingTimeComparator = (o1, o2) -> Comparator.comparingLong(Identification::waitingTime).compare(o2, o1);
    final Comparator<Identification> slaPercentageComparator = (o1, o2) -> Comparator.comparingDouble(this::getPercentageRatio).compare(o1, o2);
    final Comparator<Identification> slaTimeComparator = (o1, o2) -> Comparator.comparingInt(this::getSlaTime).compare(o2, o1);

    return identifications.stream()
                          .sorted(slaTimeComparator)
                          .sorted(slaPercentageComparator)
                          .sorted(waitingTimeComparator)
                          .sorted(DANGEROUS_ZONE_COMPARATOR)
                          .collect(Collectors.toList());
  }

  private Comparator<Identification> dangerousZoneComparator() {
    return (o11, o21) -> {
      final boolean o1Dangerous = isInDangerousZone(o11);
      final boolean o2Dangerous = isInDangerousZone(o21);
      if (o1Dangerous || o2Dangerous) {
        if (!o1Dangerous) {
          return 1;
        } else if (o2Dangerous) {
          return -1;
        } else {
          return Comparator.comparingDouble(this::getPercentageRatio).compare(o11, o21);
        }
      } else {
        return 0;
      }
    };
  }

  private boolean isInDangerousZone(final Identification identification) {
    return getPercentageRatio(identification) - 1 <= DANGER_ZONE_SLA_DELTA;
  }

  private double getPercentageRatio(final Identification c) {
    return c.getCompany().getCurrentSlaPercentage() / c.getCompany().getSlaPercentage();
  }

  private int getSlaTime(final Identification c) {
    return c.getCompany().getSlaTimeInSeconds();
  }
}
