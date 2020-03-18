package dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;

import domain.Company;
import play.db.ebean.EbeanConfig;

@Singleton
public class CompanyRepositoryImpl implements CompanyRepository {

	private final EbeanServer ebeanServer;

	@Inject
	public CompanyRepositoryImpl(EbeanConfig ebeanConfig) {
		this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
	}

	@Override
	public Company save(Company company) {
		ebeanServer.save(company);
		return company;
	}

	@Override
	public List<Company> load() {
		return ebeanServer.find(Company.class).findList();
	}

	@Override
	public Company loadById(Long id) {
		return ebeanServer.find(Company.class).where().eq("id", id).findUnique();
	}

	@Override
	public boolean existsById(Long id) {
		return ebeanServer.find(Company.class).where().eq("id", id).findRowCount() > 0;
	}
}
