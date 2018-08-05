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
  private static final double DANGER_ZONE_SLA_WAITING_TIME_RATIO_THRESHOLD = 0.25;
  private final Comparator<Identification> waitingTimeComparator = (o1, o2) -> Comparator.comparingLong(Identification::waitingTime).compare(o2, o1);
  private final Comparator<Identification> slaTimeComparator = (o1, o2) -> Comparator.comparingInt(this::slaTime).compare(o1, o2);
  private final Comparator<Identification> slaCurrentPercentageComparator = (o1, o2) -> Comparator.comparingDouble(this::currentSlaPercentage).compare(o1, o2);
  private final Comparator<Identification> dangerousZoneComparator = (o1, o2) -> {
    final boolean o1CloseToMissSla = isCloseToMissSla(o1);
    final boolean o2CloseToMissSla = isCloseToMissSla(o2);
    if (o1CloseToMissSla || o2CloseToMissSla) {
      if (!o1CloseToMissSla) {
        return -1;
      } else if (!o2CloseToMissSla) {
        return 1;
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
                          .sorted(slaCurrentPercentageComparator)
                          .sorted(waitingTimeComparator)
                          .sorted(dangerousZoneComparator)
                          .collect(Collectors.toList());
  }

  private boolean isCloseToMissSla(final Identification identification) {
    return isSlaCloseToNonAgreement(identification) && isWaitingTimeCloseToMiss(identification);
  }

  private boolean isWaitingTimeCloseToMiss(final Identification identification) {
    final int slaTime = identification.getCompany().getSlaTimeInSeconds();
    final double currentRatio = (0. + slaTime - identification.waitingTime()) / slaTime;
    return currentRatio <= DANGER_ZONE_SLA_WAITING_TIME_RATIO_THRESHOLD;
  }

  private boolean isSlaCloseToNonAgreement(final Identification identification) {
    return slaPercentageRatio(identification) - 1 <= DANGER_ZONE_SLA_PERCENTAGE_RATIO_THRESHOLD;
  }

  private double slaPercentageRatio(final Identification c) {
    return c.getCompany().getCurrentSlaPercentage() / c.getCompany().getSlaPercentage();
  }

  private int slaTime(final Identification c) {
    return c.getCompany().getSlaTimeInSeconds();
  }

  private float currentSlaPercentage(final Identification c) {
    return c.getCompany().getCurrentSlaPercentage();
  }
}
