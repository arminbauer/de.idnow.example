package repositories;

import com.avaje.ebean.Ebean;
import models.Identification;

import javax.inject.Singleton;
import java.util.List;

/**
 * @author Dmitrii Bogdanov
 * Created at 04.08.18
 */
@Singleton
public class IdentificationRepository {
  public void create(final Identification identification) {
    identification.save();
  }

  public Identification get(final long id) {
    return Ebean.find(Identification.class).where().idEq(id).findUnique();
  }

  public List<Identification> getAllPending() {
    return Ebean.find(Identification.class).where().eq("isDeleted", false).eq("isPending", true).findList();
  }

  public List<Identification> getAll() {
    return Ebean.find(Identification.class).where().eq("isDeleted", false).findList();
  }
}
