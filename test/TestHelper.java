import models.Company;
import models.Identification;
import play.libs.Json;
import play.libs.ws.WSResponse;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
@SuppressWarnings("SameParameterValue")
class TestHelper {
  private static final Random RANDOM = new Random();

  static <T> T parseObjectFromResponse(final WSResponse response, final Class<T> clazz) {
    return Json.fromJson(Json.parse(response.getBody()), clazz);
  }

  @Nonnull
  static Company buildDefaultCompany(final long id) {
    return buildCompany(id, "Test Bank", 60, 0.9f, 0.95f);
  }

  @Nonnull
  static Identification buildDefaultIdentification(final Company company) {
    return buildIdentification(RANDOM.nextLong(), company, "Peter Huber", 30);
  }

  @Nonnull
  static Identification buildIdentification(final long id, final Company company, final String username, final long waitingTime) {
    final Identification identification = new Identification();
    identification.setId(id);
    if (company == null) {
      identification.setCompanyId(null);
    } else {
      identification.setCompanyId(company.getId());
    }
    identification.setCompany(company);
    identification.setUsername(username);
    identification.setWaitingTime(waitingTime);
    identification.setStartedAt(LocalDateTime.now().minus(waitingTime, ChronoUnit.SECONDS));
    return identification;
  }

  @Nonnull
  static Company buildCompany(final long id, final String name, final int slaTime, final float slaPercentage, final float currentSlaPercentage) {
    final Company company = new Company();
    company.setId(id);
    company.setName(name);
    company.setSlaTimeInSeconds(slaTime);
    company.setSlaPercentage(slaPercentage);
    company.setCurrentSlaPercentage(currentSlaPercentage);
    return company;
  }
}
