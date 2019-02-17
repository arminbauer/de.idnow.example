package repository;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import models.WeightSettingsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Weight settings entity (DBO)
 *
 * @author Sergii R.
 * @since 17/02/19
 */
public class WeightSettingsRepository {
    private static final Logger LOG = LoggerFactory.getLogger(IdentificationRepository.class);
    private final EbeanServer ebeanServer;

    @Inject
    public WeightSettingsRepository(EbeanConfig ebeanConfig) {
        ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
    }

    public boolean save(WeightSettingsEntity weightSettingsEntity) {
        Optional<WeightSettingsEntity> weightSettings = Optional.ofNullable(ebeanServer.find(WeightSettingsEntity.class).setMaxRows(1).findUnique());
        if (weightSettings.isPresent()) {
            LOG.warn("Weight settings config already exists");
            return false;
        }
        ebeanServer.save(weightSettingsEntity);
        return true;
    }

    public WeightSettingsEntity getWeightSettings() {
        return ebeanServer.find(WeightSettingsEntity.class).setMaxRows(1).findUnique();
    }

}
