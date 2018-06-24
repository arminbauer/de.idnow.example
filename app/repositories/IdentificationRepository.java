package repositories;

import models.Identification;
import play.db.jpa.JPA;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import java.util.List;

public class IdentificationRepository extends GenericRepository<Identification> {
    public static final String LIST_ORDERED_IDENTIFICATIONS = "LIST_ORDERED_IDENTIFICATIONS";

    @Inject
    public IdentificationRepository(JPAApi jpaApi) {
        super(jpaApi);
    }

    public List<Identification> listAllPendingOrdered() {
        try {
            return jpaApi.withTransaction(
                    () -> JPA.em().createNamedQuery(LIST_ORDERED_IDENTIFICATIONS, Identification.class).getResultList()
            );
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Identification> listAllPending() {
        try {
            return jpaApi.withTransaction(() -> JPA.em().createQuery("select i from Identification i where i.pending = true", Identification.class).getResultList());
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteAll() {
        jpaApi.withTransaction(() -> {
            JPA.em().createQuery("delete from Identification").executeUpdate();
        });
    }
}
