package ua.nure.repository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ua.nure.domain.Department;
import ua.nure.domain.Employee;
import ua.nure.domain.Job;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class EmployeeRepository implements CrudRepository<Employee> {


    private static final String TABLE_NAME = "employee";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Employee employee) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement("insert into " + TABLE_NAME + "(email, first_name, hire_date, last_name, phone_number, " +
                "salary, department_id, manager_id) VALUES(?,?,?,?,?,?,?,?)", new String[] {"id"});
                    ps.setString(1, employee.getEmail());
                    ps.setString(2, employee.getFirstName());
                    ps.setDate(3, Date.valueOf(employee.getHireDate().atZone(ZoneId.systemDefault()).toLocalDate()));
                    ps.setString(4, employee.getLastName());
                    ps.setString(5, employee.getPhoneNumber());
                    ps.setLong(6, employee.getSalary());
                    ps.setLong(7, employee.getDepartment().getId());
                    ps.setLong(8, employee.getManager().getId());
                    return ps;
                },
                keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Employee employee) {
        String updateEmployee = "update " + TABLE_NAME + " set email = ?, first_name = ?, hire_date = ?, " +
                " last_name = ?, phone_number = ?, salary = ?, department_id = ?, manager_id = ? where id = ?";
        this.jdbcTemplate.update(updateEmployee, employee.getEmail(), employee.getFirstName(), employee.getHireDate(), employee.getLastName(),
                employee.getPhoneNumber(), employee.getSalary(), employee.getDepartment().getId(), employee.getManager().getId(), employee.getId());
    }

    @Override
    public Employee findOne(Integer id) {
        String selectOneEmployee = "select e.id, e.email, e.first_name, " +
                "e.hire_date, e.last_name, e.phone_number, e.salary, e.department_id, e.manager_id , d.department_name, d.location_id " +
                " from employee e left join department d on e.department_id = d.id where e.id = ?";
        return this.jdbcTemplate.queryForObject(selectOneEmployee, new Object[]{id}, employeeMapper::apply);
    }

    @Override
    public List<Employee> findAll() {
        String selectAllEmployees = "select e.id, e.email, e.first_name, " +
                "e.hire_date, e.last_name, e.phone_number, e.salary, e.department_id, e.manager_id , d.id, d.department_name, d.location_id " +
                " from employee e left join department d on e.department_id = d.id ";
        return jdbcTemplate.query(selectAllEmployees, employeeMapper::apply);
    }

    @Override
    public void delete(Integer id) {
        String deleteEmployee = "delete from " + TABLE_NAME + " where id = ?";
        this.jdbcTemplate.update(deleteEmployee, id);
    }

    private Set<Job> findAllEmployeesJobs(Long id) {
        String selectJobsByEmployeeId = "select j.id, j.job_title, j.max_salary, j.min_salary, j.employee_id from job j where j.employee_id = ?";
        return new HashSet<>(this.jdbcTemplate.query(selectJobsByEmployeeId, new Object[]{id}, (resultSet, i) -> { // added new Object[]{id}, as a parameter j.employee_id
            Job job = new Job();
            job.setId(resultSet.getLong(1));
            job.setJobTitle(resultSet.getString(2));
            job.setMinSalary(resultSet.getLong(4));
            job.setMaxSalary(resultSet.getLong(3));
            job.setEmployee(findOne(id.intValue()));
            return job;
        }));
    }

    private BiFunction<ResultSet, Integer, Employee> employeeMapper = (resultSet, integer) -> {
        try {
            Employee employee = new Employee();
            employee.setId(resultSet.getLong(1));
            employee.setEmail(resultSet.getString(2));
            employee.setFirstName(resultSet.getString(3));
            employee.setHireDate(resultSet.getTimestamp(4).toInstant());
            employee.setLastName(resultSet.getString(5));
            employee.setPhoneNumber(resultSet.getString(6));
            employee.setSalary(resultSet.getLong(7));

            //department setting
            Department departmentForThisEmployee = new Department();
            departmentForThisEmployee.setId(resultSet.getLong(8));
            departmentForThisEmployee.setDepartmentName(resultSet.getString(10));

            //setting manager(employee)
            Employee managerForThisEmployee = findOne(resultSet.getInt(9));
            employee.setManager(managerForThisEmployee);

            // Jobs Set set
            employee.setJobs(findAllEmployeesJobs(resultSet.getLong(1)));

            return employee;

        } catch (SQLException e) {
            throw new DataIntegrityViolationException(e.getSQLState());
        }
    };
}
