package repositories;

import com.avaje.ebean.Ebean;
import models.Company;
import models.Identification;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.validation.ValidationException;
import java.util.List;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
@Singleton
public class IdentificationRepository {
  private final CompanyRepository companyRepository;

  @Inject
  public IdentificationRepository(final CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }

  public void create(@Nonnull final Identification identification) {
    final Long companyId = identification.getCompanyId();
    if (companyId == null && identification.getCompany() == null) {
      throw new ValidationException("CompanyId is not set for identification");
    }
    if (identification.getCompany() == null && companyId != null) {
      final Company company = companyRepository.getById(companyId);
      if (company == null) {
        throw new PersistenceException("Company with id " + companyId + " doesn't exist");
      } else {
        identification.setCompany(company);
      }
    }
    identification.save();
    identification.refresh();
  }

  public Identification getById(final long id) {
    final Identification unique = Ebean.find(Identification.class).where().idEq(id).findUnique();
    if (unique == null) {
      throw new EntityNotFoundException(Identification.class.getSimpleName());
    } else {
      return unique;
    }
  }

  public List<Identification> allPending() {
    return Ebean
        .find(Identification.class)
        .fetch("company")
        .where()
        .eq("isDeleted", false)
        .eq("isPending", true)
        .orderBy("startedAt")
        .findList();
  }

  public List<Identification> all() {
    return Ebean
        .find(Identification.class)
        .fetch("company")
        .where()
        .eq("isDeleted", false)
        .findList();
  }
}
