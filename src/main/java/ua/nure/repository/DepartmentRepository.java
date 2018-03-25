package ua.nure.repository;

import org.springframework.dao.DataIntegrityViolationException;
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
import java.util.function.BiFunction;

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
        return this.jdbcTemplate.queryForObject(selectOneDepartment, new Object[]{id}, departmentMapper::apply);
    }

    @Override
    public List<Department> findAll() {
        String selectAllDepartments = "select d.id, d.department_name, d.location_id, " +
                "l.id, l.city, l.postal_code, l.street_address, l.country_id from department d left join location l " +
                "on d.location_id = l.id";
        return this.jdbcTemplate.query(selectAllDepartments, departmentMapper::apply);
    }

    @Override
    public void delete(Integer id) {
        String deleteDepartment = "delete from " + TABLE_NAME + " where id = ?";
        jdbcTemplate.update(deleteDepartment, id);
    }

    private  Set<Employee> findAllEmployeesInDepartment (Long id) {
        String selectEmployeeByDepId = "select e.id, e.email, e.first_name, e.hire_date, " +
                "e.last_name, e.phone_number, e.salary, e.department_id, e.manager_id from " +
                "employee e where e.department_id = ?";
        return new HashSet<>(this.jdbcTemplate.query(selectEmployeeByDepId, new Object[]{id}, (resultSet, i) -> {
            Employee employee = new Employee();
            employee.setId(resultSet.getLong(1));
            employee.setEmail(resultSet.getString(2));
            employee.setFirstName(resultSet.getString(3));
            employee.setHireDate(resultSet.getTimestamp(4).toInstant());
            employee.setLastName(resultSet.getString(5));
            employee.setPhoneNumber(resultSet.getString(6));
            employee.setSalary(resultSet.getLong(7));

            //not set: jobs, Department, manager a.k.a another Employee
            return employee;
        }));
    }

    @Override
    public void update(Department department) {
        String updateDepartment = "update " + TABLE_NAME + " set department_name = ?, location_id = ? where id = ?";
        this.jdbcTemplate.update(updateDepartment, department.getDepartmentName(), department.getLocation().getId(), department.getId());
    }

    private BiFunction<ResultSet, Integer, Department> departmentMapper = (resultSet, i) -> {
        try {
            Department department = new Department();
            department.setId(resultSet.getLong(1));
            department.setDepartmentName(resultSet.getString(2));
            if (resultSet.getLong(3) != 0) {
                Location locationForDepartment = new Location();
                locationForDepartment.setId(resultSet.getLong(4));
                locationForDepartment.setCity(resultSet.getString(5));
                locationForDepartment.setPostalCode(resultSet.getString(6));
                locationForDepartment.setStreetAddress(resultSet.getString(7));
                //we also need to setCountry, we have l.country_id. but we need to do 1 more query
                department.setLocation(locationForDepartment);
            }
            department.setEmployees(findAllEmployeesInDepartment(resultSet.getLong(1)));
            return department;
        } catch (SQLException e) {
            throw new DataIntegrityViolationException(e.getSQLState());
        }
    };
}
