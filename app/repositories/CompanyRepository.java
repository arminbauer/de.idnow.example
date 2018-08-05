package repositories;

import com.avaje.ebean.Ebean;
import models.Company;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
@Singleton
public class CompanyRepository {
  public void create(@Nonnull final Company company) {
    company.save();
    company.refresh();
  }

  public Company getById(final long id) {
    final Company unique = Ebean
        .find(Company.class)
        .where()
        .idEq(id)
        .orderBy("id")
        .findUnique();
    if (unique == null) {
      throw new EntityNotFoundException(Company.class.getSimpleName());
    } else {
      return unique;
    }
  }

  public List<Company> all() {
    return Ebean
        .find(Company.class)
        .where()
        .eq("isDeleted", false)
        .orderBy()
        .asc("id")
        .findList();
  }
}
