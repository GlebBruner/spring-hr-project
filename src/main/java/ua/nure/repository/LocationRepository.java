package ua.nure.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ua.nure.domain.Country;
import ua.nure.domain.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LocationRepository implements CrudRepository<Location>{

    private static final String TABLE_NAME = "location";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Location location) {
//        this.jdbcTemplate.update(insertLocation, location.getCity(), location.getPostalCode(), location.getStreetAddress(), location.getCountry().getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement("insert into " + TABLE_NAME + " (city, postal_code, street_address, country_id) " +
                                    " values (?, ?, ?, ?)", new String[] {"id"});
                    ps.setString(1, location.getCity());
                    ps.setString(2, location.getPostalCode());
                    ps.setString(3, location.getStreetAddress());
                    ps.setLong(3, location.getCountry().getId());
                    return ps;
                },
                keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Location findOne(Integer id) {
        String selectLocation = "select l.id, l.city. l.postal_code, l.street_address, l.country_id, c.id, c.country_name " +
                " from location l LEFT JOIN country c ON l.country_id = c.id" + " where l.id = ?";
        return this.jdbcTemplate.queryForObject(selectLocation, new Object[]{id}, new LocationMapper());
    }

    @Override
    public List<Location> findAll() {
        String selectAllLocations = "select l.id, l.city. l.postal_code, l.street_address, l.country_id, c.id, c.country_name " +
                " from location l LEFT JOIN country c ON l.country_id = c.id";
        return this.jdbcTemplate.query(selectAllLocations, new LocationMapper());
    }

    @Override
    public void delete(Integer id) {
        String deleteLocation = "delete from " + TABLE_NAME + " where id = ?";
        this.jdbcTemplate.update(deleteLocation, id);
    }

    private static final class LocationMapper implements RowMapper<Location> {
        @Override
        public Location mapRow(ResultSet resultSet, int i) throws SQLException {
            Location location = new Location();
            location.setId(resultSet.getLong("l.id"));
            location.setCity(resultSet.getString("l.city"));
            location.setPostalCode(resultSet.getString("l.postal_code"));
            location.setStreetAddress(resultSet.getString("l.street_address"));
            location.setCountry(new Country(resultSet.getLong("c.id"), resultSet.getString("c.country_name")));
            return location;
        }
    }
}