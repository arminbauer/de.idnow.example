package dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;

import domain.Identification;
import play.db.ebean.EbeanConfig;

@Singleton
public class IdentificationRepositoryImpl implements IdentificationRepository {

	private final EbeanServer ebeanServer;

	@Inject
	public IdentificationRepositoryImpl(EbeanConfig ebeanConfig) {
		this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
	}

	@Override
	public Identification save(Identification identification) {
		ebeanServer.save(identification);
		return identification;
	}

	@Override
	public List<Identification> load() {
		return ebeanServer.find(Identification.class).findList();
	}

}
