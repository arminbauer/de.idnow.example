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
  private static final double DANGER_ZONE_SLA_PERCENTAGE_RATIO_THRESHOLD = 0.05;
  private static final double DANGER_ZONE_SLA_WAITING_TIME_RATIO_THRESHOLD = 0.4;
  private final Comparator<Identification> waitingTimeComparator = (o1, o2) -> Comparator.comparingLong(Identification::waitingTime).compare(o2, o1);
  private final Comparator<Identification> slaPercentageComparator = (o1, o2) -> Comparator.comparingDouble(this::getSlaPercentageRatio).compare(o1, o2);
  private final Comparator<Identification> slaTimeComparator = (o1, o2) -> Comparator.comparingInt(this::getSlaTime).compare(o2, o1);
  private final Comparator<Identification> dangerousZoneComparator = (o1, o2) -> {
    final boolean o1DangerousInTermsOfMissedSla = isInDangerousZone(o1);
    final boolean o2DangerousInTermsOfMissedSla = isInDangerousZone(o2);
    if (o1DangerousInTermsOfMissedSla || o2DangerousInTermsOfMissedSla) {
      if (!o1DangerousInTermsOfMissedSla) {
        return 1;
      } else if (!o2DangerousInTermsOfMissedSla) {
        return -1;
      } else {
        return 0;
      }
    } else {
      return 0;
    }
  };

  public List<Identification> sortBySla(final List<Identification> identifications) {
    return identifications.stream()
                          .sorted(slaTimeComparator)
                          .sorted(slaPercentageComparator)
                          .sorted(waitingTimeComparator)
                          .sorted(dangerousZoneComparator)
                          .collect(Collectors.toList());
  }

  private boolean isInDangerousZone(final Identification identification) {
    return isSlaCloseToNonAgreement(identification) && isWaitingTimeCloseToMiss(identification);
  }

  private boolean isWaitingTimeCloseToMiss(final Identification identification) {
    final int slaTime = identification.getCompany().getSlaTimeInSeconds();
    final double currentRatio = (0. + slaTime - identification.waitingTime()) / slaTime;
    return currentRatio <= DANGER_ZONE_SLA_WAITING_TIME_RATIO_THRESHOLD;
  }

  private boolean isSlaCloseToNonAgreement(final Identification identification) {
    return getSlaPercentageRatio(identification) - 1 <= DANGER_ZONE_SLA_PERCENTAGE_RATIO_THRESHOLD;
  }

  private double getSlaPercentageRatio(final Identification c) {
    return c.getCompany().getCurrentSlaPercentage() / c.getCompany().getSlaPercentage();
  }

  private int getSlaTime(final Identification c) {
    return c.getCompany().getSlaTimeInSeconds();
  }
}
