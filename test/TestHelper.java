import models.Company;
import models.Identification;
import play.libs.Json;
import play.libs.ws.WSResponse;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
@SuppressWarnings("SameParameterValue")
class TestHelper {
  static <T> T parseObjectFromResponse(final WSResponse response, final Class<T> clazz) {
    return Json.fromJson(Json.parse(response.getBody()), clazz);
  }

  @Nonnull
  static Company buildDefaultCompany() {
    return buildCompany(-100L, "Test Bank", 60, 0.9f, 0.95f);
  }

  @Nonnull
  static Identification buildDefaultIdentification(final Company company) {
    return buildIdentification(-100L, company, "Peter Huber", LocalDateTime.ofInstant(Instant.ofEpochSecond(1435667215L), ZoneId.of("UTC")));
  }

  @Nonnull
  static Identification buildIdentification(final long id, final Company company, final String username, final LocalDateTime startedAt) {
    final Identification identification = new Identification();
    identification.setId(id);
    identification.setCompanyId(company.getId());
    identification.setUsername(username);
    identification.setStartedAt(startedAt);
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
