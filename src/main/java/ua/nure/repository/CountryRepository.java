package ua.nure.repository;

import ua.nure.domain.Country;

import javax.sql.DataSource;
import java.util.List;

public class CountryRepository implements CrudRepository<Country> {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Country country) {

    }

    @Override
    public Country findOne(Integer id) {
        return null;
    }

    @Override
    public List<Country> findAll() {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
