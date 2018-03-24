package ua.nure.service;

import ua.nure.domain.Employee;
import ua.nure.repository.EmployeeRepository;

import java.util.List;

public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee findOne(Integer id) {
        return this.employeeRepository.findOne(id);
    }

    public List<Employee> findAll () {
        return this.employeeRepository.findAll();
    }

    public void create (Employee employee) {
        this.employeeRepository.save(employee);
    }

    public void delete(Integer id) {
        this.employeeRepository.delete(id);
    }

    public boolean isEmployeeExists (Integer id) {
        return this.employeeRepository.findOne(id) != null;
    }

}
