package ua.nure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.nure.domain.Country;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CountryRepository implements CrudRepository<Country> {

    private static final String TABLE_NAME = "country";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Country country) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement("insert into country(country_name) values (?)", new String[] {"id"});
                    ps.setString(1, country.getCountryName());
                    return ps;
                },
                keyHolder);
        return keyHolder.getKey().longValue();
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

    @Override
    public void update(Country country) {
        String updateCountry = "update " + TABLE_NAME  + " set country_name = ? where id = ?";
        this.jdbcTemplate.update(updateCountry, country.getCountryName(), country.getId());
    }
}
