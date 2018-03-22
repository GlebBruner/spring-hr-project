package ua.nure.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.nure.domain.Country;

import javax.sql.DataSource;
import java.util.List;

public class CountryRepository implements CrudRepository<Country> {

//    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //    public void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;
//        this.jdbcTemplate = new JdbcTemplate(dataSource);
//    }

    @Override
    public void save(Country country) {
        jdbcTemplate.update("insert into country(country_name) values (?)", country.getCountryName());
    }

    @Override
    public Country findOne(Integer id) {
        return jdbcTemplate.queryForObject("select * from country where id = ?",
                new Object[]{id}, (rs, i) -> new Country(rs.getLong(1), rs.getString(2)));
    }

    @Override
    public List<Country> findAll() {
        return jdbcTemplate.query("select * from country", (rs, rowNum) -> new Country(rs.getLong(1), rs.getString(2)));
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("delete from country where id = ?", id);
    }


}
