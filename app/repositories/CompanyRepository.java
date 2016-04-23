package repositories;

import com.avaje.ebean.Ebean;
import repositories.models.Company;

import java.util.List;

/**
 * Created by ebajrami on 4/23/16.
 */
public class CompanyRepository implements Repository<Company> {
    @Override
    public Company findById(int id) {
        return Ebean.find(Company.class, id);
    }

    @Override
    public void add(Company object) {
        Ebean.save(object);
    }

    @Override
    public void update(Company object) {
        Ebean.update(object);
    }

    @Override
    public void delete(Company object) {
        Ebean.delete(object);
    }

    @Override
    public void deleteById(Company object) {
        Company company = findById(object.getId());
        if (company != null) {
            delete(company);
        }
    }

    @Override
    public List<Company> findAll() {
        return Ebean.find(Company.class).findList();
    }
}
