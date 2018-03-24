package ua.nure.repository;

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

public class EmployeeRepository implements CrudRepository<Employee> {


    private static final String TABLE_NAME = "employee";

    private JdbcTemplate jdbcTemplate;


    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Employee employee) {
//        String insertEmployee = "insert into " + TABLE_NAME + "(email, first_name, hire_date, last_name, phone_number, " +
//                "salary, department_id, manager_id) VALUES(?,?,?,?,?,?,?,?)";
//        this.jdbcTemplate.update(insertEmployee, employee.getEmail(), employee.getFirstName(), employee.getHireDate(),
//                employee.getLastName(), employee.getPhoneNumber(), employee.getSalary(), employee.getDepartment().getId(), employee.getManager().getId());

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
    public Employee findOne(Integer id) {
        String selectOneEmployee = "select e.id, e.email, e.first_name, " +
                "e.hire_date, e.last_name, e.phone_number, e.salary, e.department_id, e.manager_id , d.id, d.department_name, d.location_id " +
                " from employee e left join department d on e.department_id = d.id where e.id = ?";
        return this.jdbcTemplate.queryForObject(selectOneEmployee, new Object[]{id}, new RowMapper<Employee>() {
            @Override
            public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("e.id"));
                employee.setFirstName(resultSet.getString("e.first_name"));
                employee.setLastName(resultSet.getString("e.last_name"));
                employee.setHireDate(resultSet.getTimestamp("e.hire_date").toInstant());
                employee.setEmail(resultSet.getString("e.email"));
                employee.setPhoneNumber(resultSet.getString("e.phone_number"));
                employee.setSalary(resultSet.getLong("e.salary"));

                //department setting
                Department departmentForThisEmployee = new Department();
                departmentForThisEmployee.setId(resultSet.getLong("d.id"));
                departmentForThisEmployee.setDepartmentName(resultSet.getString("d.department_name"));

                //setting manager(employee)
                Employee managerForThisEmployee = findOne(resultSet.getInt("e.manager"));
                employee.setManager(managerForThisEmployee);

                employee.setJobs(findAllEmployeesJobs(id));

                return employee;
            }
        });
    }

    @Override
    public List<Employee> findAll() {
        String selectAllEmployees = "select e.id, e.email, e.first_name, " +
                "e.hire_date, e.last_name, e.phone_number, e.salary, e.department_id, e.manager_id , d.id, d.department_name, d.location_id " +
                " from employee e left join department d on e.department_id = d.id ";
        return jdbcTemplate.query(selectAllEmployees, new RowMapper<Employee>() {
            @Override
            public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("e.id"));
                employee.setFirstName(resultSet.getString("e.first_name"));
                employee.setLastName(resultSet.getString("e.last_name"));
                employee.setHireDate(resultSet.getTimestamp("e.hire_date").toInstant());
                employee.setEmail(resultSet.getString("e.email"));
                employee.setPhoneNumber(resultSet.getString("e.phone_number"));
                employee.setSalary(resultSet.getLong("e.salary"));

                //department setting
                Department departmentForThisEmployee = new Department();
                departmentForThisEmployee.setId(resultSet.getLong("d.id"));
                departmentForThisEmployee.setDepartmentName(resultSet.getString("d.department_name"));

                //setting manager(employee)
                Employee managerForThisEmployee = findOne(resultSet.getInt("e.manager"));
                employee.setManager(managerForThisEmployee);

                employee.setJobs(findAllEmployeesJobs((int) resultSet.getLong("e.id")));

                return employee;
            }
        });
    }

    @Override
    public void delete(Integer id) {
        String deleteEmployee = "delete from " + TABLE_NAME + " where id = ?";
        this.jdbcTemplate.update(deleteEmployee, id);
    }

    private Set<Job> findAllEmployeesJobs(Integer id) {
        String selectJobsByEmployeeId = "select j.id, j.job_title, j.max_salary, j.min_salary, j.employee_id from job j where j.employee_id = ?";
        return new HashSet<Job>(this.jdbcTemplate.query(selectJobsByEmployeeId, new RowMapper<Job>() {
            @Override
            public Job mapRow(ResultSet resultSet, int i) throws SQLException {
                Job job = new Job();
                job.setId(resultSet.getLong("j.id"));
                job.setJobTitle(resultSet.getString("j.job_title"));
                job.setMinSalary(resultSet.getLong("j.min_salary"));
                job.setMaxSalary(resultSet.getLong("j.max_salary"));
                job.setEmployee(findOne(id));
                return job;
            }
        }));
    }

}
