package repository;

import com.avaje.ebean.EbeanServer;
import models.IdentificationEntity;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Company entity (DBO)
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class IdentificationRepository {

    private final EbeanServer ebeanServer;

    @Inject
    public IdentificationRepository(EbeanServer ebeanServer) {
        this.ebeanServer = ebeanServer;
    }

    public void save(IdentificationEntity identificationEntity) {
        ebeanServer.save(identificationEntity);
    }

    public IdentificationEntity getById(Long id) {
        return ebeanServer.find(IdentificationEntity.class).setId(id).findUnique();
    }

    public void delete(Long id) {
        Optional<IdentificationEntity> identificationEntity = Optional.ofNullable(ebeanServer.find(IdentificationEntity.class).setId(id).findUnique());
        identificationEntity.ifPresent(ebeanServer::delete);
    }
}
