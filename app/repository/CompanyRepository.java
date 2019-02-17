package repository;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import models.CompanyEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Company entity (DBO)
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class CompanyRepository {

    private final EbeanServer ebeanServer;
    private static final Logger LOG = LoggerFactory.getLogger(CompanyRepository.class);

    @Inject
    public CompanyRepository(EbeanConfig ebeanConfig) {
        ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
    }

    public boolean save(CompanyEntity companyEntity) {
        Optional<CompanyEntity> identificationEntity = Optional.ofNullable(ebeanServer.find(CompanyEntity.class).setId(companyEntity.getId()).findUnique());
        if (identificationEntity.isPresent()) {
            LOG.warn("Company with id {} already exists", companyEntity.getId());
            return false;
        }
        ebeanServer.save(companyEntity);
        return true;
    }

    public CompanyEntity getById(Long id) {
        return ebeanServer.find(CompanyEntity.class).setId(id).findUnique();
    }

    public void delete(Long id) {
        Optional<CompanyEntity> companyEntity = Optional.ofNullable(ebeanServer.find(CompanyEntity.class).setId(id).findUnique());
        companyEntity.ifPresent(ebeanServer::delete);
    }
}

