package ua.nure.repository;

import ua.nure.domain.Employee;

public interface EmployeeRepository extends CrudRepository<Employee> {

    Long getAverageSalary();
}
