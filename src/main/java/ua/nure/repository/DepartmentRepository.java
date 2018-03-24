package ua.nure.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ua.nure.domain.Department;
import ua.nure.domain.Employee;
import ua.nure.domain.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DepartmentRepository implements CrudRepository<Department> {


    private static final String TABLE_NAME = "department";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Department department) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement("insert into " + TABLE_NAME + " (department_name, location_id) values (?, ?)", new String[] {"id"});
                    ps.setString(1, department.getDepartmentName());
                    ps.setLong(2, department.getLocation().getId());
                    return ps;
                },
                keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Department findOne(Integer id) {
        String selectOneDepartment = "select d.id, d.department_name, d.location_id, " +
                "l.id, l.city, l.postal_code, l.street_address, l.country_id from department d left join location l " +
                "on d.location_id = l.id where d.id = ?";
        return this.jdbcTemplate.queryForObject(selectOneDepartment, new Object[]{id}, new RowMapper<Department>() {
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

                department.setEmployees(findAllEmployeesInDepartment(resultSet.getLong("d.id")));

                return department;
            }
        });
    }

    @Override
    public List<Department> findAll() {
        String selectAllDepartments = "select d.id, d.department_name, d.location_id, " +
                "l.id, l.city, l.postal_code, l.street_address, l.country_id from department d left join location l " +
                "on d.location_id = l.id";
//        return this.jdbcTemplate.query(selectAllDepartments, new DepartmentMapper());
        return this.jdbcTemplate.query(selectAllDepartments, new RowMapper<Department>() {
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

                department.setEmployees(findAllEmployeesInDepartment(resultSet.getLong("d.id")));

                return department;
            }
        });
    }

    @Override
    public void delete(Integer id) {
        String deleteDepartment = "delete from " + TABLE_NAME + " where id = ?";
        jdbcTemplate.update(deleteDepartment, id);
    }

    private  Set<Employee> findAllEmployeesInDepartment (Long id) {
        String selectEmployeeByDepId = "select e.id, e.email, e.first_name, e.hire_date, " +
                "e.last_name, e.phone_number, e.salary, e.department_id, e.manager id from " +
                "employee e where e.department_id = ?";
        return new HashSet<Employee>(this.jdbcTemplate.query(selectEmployeeByDepId, new RowMapper<Employee>() {
            @Override
            public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("e.id"));
                employee.setFirstName(resultSet.getString("e.first_name"));
                employee.setHireDate(resultSet.getTimestamp("e.hire_date").toInstant());
                employee.setLastName(resultSet.getString("e.last_name"));
                employee.setPhoneNumber(resultSet.getString("e.phone_number"));
                employee.setSalary(resultSet.getLong("e.salary"));

                //not set: jobs, Department, manager a.k.a another Employee
                return employee;
            }
        }));
    }

    @Override
    public void update(Department department) {
        String updateDepartment = "update " + TABLE_NAME + " set department_name = ?, location_id = ? where id = ?";
        this.jdbcTemplate.update(updateDepartment, department.getDepartmentName(), department.getLocation().getId(), department.getId());
    }
}
