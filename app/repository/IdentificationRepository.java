package repository;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import models.IdentificationEntity;
import models.IdentificationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Identification repository
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class IdentificationRepository {

    private static final Logger LOG = LoggerFactory.getLogger(IdentificationRepository.class);
    private final EbeanServer ebeanServer;

    @Inject
    public IdentificationRepository(EbeanConfig ebeanConfig) {
        ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
    }

    public boolean save(IdentificationEntity identificationEntity) {
        Optional<IdentificationEntity> identification = Optional.ofNullable(ebeanServer.find(IdentificationEntity.class).setId(identificationEntity.getId()).findUnique());
        if (identification.isPresent()) {
            LOG.warn("Identification with id {} already exists", identificationEntity.getId());
            return false;
        }
        ebeanServer.save(identificationEntity);
        return true;
    }

    public IdentificationEntity getById(Long id) {
        return ebeanServer.find(IdentificationEntity.class).setId(id).findUnique();
    }

    public List<IdentificationEntity> getByStatus(IdentificationStatus identificationStatus) {
        return ebeanServer.find(IdentificationEntity.class)
                .where().eq("identificationStatus", identificationStatus).findList();
    }

    public void delete(Long id) {
        Optional<IdentificationEntity> identificationEntity = Optional.ofNullable(ebeanServer.find(IdentificationEntity.class).setId(id).findUnique());
        identificationEntity.ifPresent(ebeanServer::delete);
    }
}
