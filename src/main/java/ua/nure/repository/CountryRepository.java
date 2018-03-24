package ua.nure.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ua.nure.domain.Country;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CountryRepository implements CrudRepository<Country> {

    private JdbcTemplate jdbcTemplate;

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
        String updateLocation = "update country set country_name = ? where id = ?";
        this.jdbcTemplate.update(updateLocation, country.getCountryName(), country.getId());
    }
}
