package ua.nure.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ua.nure.domain.Department;
import ua.nure.domain.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentRepository implements CrudRepository<Department> {


    private static final String TABLE_NAME = "department";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Department department) {
        String insertDepartment = "insert into " + TABLE_NAME + " (id, department_name, location_id) values (?, ?, ?)";
        this.jdbcTemplate.update(insertDepartment, department.getId(), department.getDepartmentName(), department.getLocation().getId());
    }

    @Override
    public Department findOne(Integer id) {
        String selectOneDepartment = "select d.id, d.department_name, d.location_id, " +
                "l.id, l.city, l.postal_code, l.street_address, l.country_id from department d left join location l " +
                "on d.location_id = l.id where d.id = ?";
        return this.jdbcTemplate.queryForObject(selectOneDepartment, new Object[]{id}, new DepartmentMapper());
    }

    @Override
    public List<Department> findAll() {
        String selectAllDepartments = "select d.id, d.department_name, d.location_id, " +
                "l.id, l.city, l.postal_code, l.street_address, l.country_id from department d left join location l " +
                "on d.location_id = l.id";
        return this.jdbcTemplate.query(selectAllDepartments, new DepartmentMapper());
    }

    @Override
    public void delete(Integer id) {
        String deleteDepartment = "delete from " + TABLE_NAME + " where id = ?";
        jdbcTemplate.update(deleteDepartment, id);
    }

    private static final class DepartmentMapper implements RowMapper<Department> {
        @Override
        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
            Department department = new Department();
            department.setId(resultSet.getLong("d.id"));
            department.setDepartmentName(resultSet.getString("d.department_name"));

            if (resultSet.getLong("d.location_id") != 0) {
                Location locationForThisDepartment = new Location();
                locationForThisDepartment.setId(resultSet.getLong("l.id"));
                locationForThisDepartment.setCity(resultSet.getString("l.city"));
                locationForThisDepartment.setPostalCode(resultSet.getString("l.postal_code"));
                locationForThisDepartment.setStreetAddress(resultSet.getString("l.street_address"));
                department.setLocation(locationForThisDepartment);
            }

            return department;
        }
    }


}
