package ua.nure.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ua.nure.domain.Department;
import ua.nure.domain.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeRepository implements CrudRepository<Employee> {


    private static final String TABLE_NAME = "employee";

    private JdbcTemplate jdbcTemplate;


    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void save(Employee employee) {

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

                Department departmentForThisEmployee = new Department();
                departmentForThisEmployee.setId(resultSet.getLong("d.id"));
                departmentForThisEmployee.setDepartmentName(resultSet.getString("d.department_name"));
//                departmentForThisEmployee.setEmployees(); DepRepo.findAllEmployeesByDepId
//                departmentForThisEmployee.setLocation();???????

                Employee managerForThisEmployee = findOne(resultSet.getInt("e.manager"));
                employee.setManager(managerForThisEmployee);// ??????

                return employee;
            }
        });
    }

    @Override
    public List<Employee> findAll() {
        String selectAllEmployees = "select * from " + TABLE_NAME;
        return jdbcTemplate.query(selectAllEmployees, new RowMapper<Employee>() {
            @Override
            public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
                return null;
                //
            }
        });
    }

    @Override
    public void delete(Integer id) {

    }




}
