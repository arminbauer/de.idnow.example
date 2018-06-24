package repositories;

import play.db.jpa.JPA;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class GenericRepository<T> {

    private final JPAApi jpaApi;

    @Inject
    public GenericRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Transactional
    public void save(T entity) {
        jpaApi.withTransaction(() -> {
            EntityManager em = JPA.em();
            em.persist(entity);
        });
    }
}
