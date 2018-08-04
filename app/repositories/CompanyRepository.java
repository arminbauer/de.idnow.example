package repositories;

import com.avaje.ebean.Ebean;
import models.Company;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import java.util.List;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
@Singleton
public class CompanyRepository {
  public void create(@Nonnull final Company company) {
    company.save();
  }

  public Company get(final long id) {
    return Ebean.find(Company.class).where().idEq(id).orderBy("id").findUnique();
  }

  public List<Company> all() {
    return Ebean.find(Company.class).where().eq("isDeleted", false).orderBy().asc("id").findList();
  }
}
