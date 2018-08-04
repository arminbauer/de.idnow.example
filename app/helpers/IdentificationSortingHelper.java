package helpers;

import models.Identification;

import javax.inject.Singleton;
import java.util.Comparator;
import java.util.List;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
@Singleton
public class IdentificationSortingHelper {
  public List<Identification> sortBySla(final List<Identification> identifications) {
    identifications.sort((o1, o2) -> Comparator.comparingLong(Identification::waitingTime).compare(o2, o1));
    return identifications;
  }
}
