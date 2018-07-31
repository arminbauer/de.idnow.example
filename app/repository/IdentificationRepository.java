package repository;

import model.Identification;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class IdentificationRepository {
  private static PriorityQueue<Identification> PENDING_IDENTIFICATIONS = new PriorityQueue<>();
  private static Map<Long, Identification> ACCEPTED_IDENTIFICATIONS = new HashMap<>();

  public static void addIdentification(Identification identification) {
    if (!PENDING_IDENTIFICATIONS.contains(identification)) {
      PENDING_IDENTIFICATIONS.add(identification);
    }
  }

  public static void acceptIdentification(Identification identification) {
    if (PENDING_IDENTIFICATIONS.contains(identification)) {
      ACCEPTED_IDENTIFICATIONS.put(identification.getId(), identification);
      PENDING_IDENTIFICATIONS.remove(identification);
    }
  }

  public static void setPendingIdentifications(
      PriorityQueue<Identification> pendingIdentifications) {
    PENDING_IDENTIFICATIONS = pendingIdentifications;
  }

  public static void resetPendingIdentifications() {
    PENDING_IDENTIFICATIONS.clear();
  }

  public static void setAcceptedIdentifications(Map<Long, Identification> acceptedIdentifications) {
    ACCEPTED_IDENTIFICATIONS = acceptedIdentifications;
  }

  public static PriorityQueue<Identification> getPendingIdentifications() {
    return PENDING_IDENTIFICATIONS;
  }
}
