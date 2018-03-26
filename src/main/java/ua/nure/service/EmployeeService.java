package ua.nure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.domain.Employee;
import ua.nure.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee findOne(Integer id) {
        return this.employeeRepository.findOne(id);
    }

    public List<Employee> findAll () {
        return this.employeeRepository.findAll();
    }

    public Long create(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    public void delete(Integer id) {
        this.employeeRepository.delete(id);
    }

    public void update (Employee employee) {
        this.employeeRepository.update(employee);
    }

}
