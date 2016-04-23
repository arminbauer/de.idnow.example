package repositories;

import models.Company;

/**
 * Created by ebajrami on 4/23/16.
 */
public class CompanyRepository implements Repository<Company> {


    @Override
    public Company findById(int id) {

        return null;
    }

    @Override
    public void add(Company object) {

    }

    @Override
    public void update(Company object) {

    }

    @Override
    public void delete(Company object) {

    }

    @Override
    public void deleteById(Company object) {

    }

    @Override
    public Iterable<Company> findAll() {
        return null;
    }
}
