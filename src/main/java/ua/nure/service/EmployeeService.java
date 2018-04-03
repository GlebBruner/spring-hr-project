package ua.nure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.domain.Employee;
import ua.nure.repository.EmployeeRepository;
import ua.nure.repository.jdbc.EmployeeRepositoryImpl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional(readOnly = true)
    public Long getAvgNonManagerSalary() {
        return this.employeeRepository.getAverageSalary();
    }

    @Transactional(readOnly = true)
    public Employee findOne(Integer id) {
        return this.employeeRepository.findOne(id);
    }

    @Transactional(readOnly = true)
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

    public Map<Employee, Double> getCandidatesforRaise(double coef) throws IllegalArgumentException {

        if (coef < 0) {
            throw new IllegalArgumentException();
        } else {

            return this.employeeRepository.findAll()
                    .stream()
                    .filter(employee -> ChronoUnit.MONTHS.between(employee.getHireDate(), Instant.now()) >= 6 && employee.getSalary() < getAvgNonManagerSalary())
                    .collect(Collectors.toMap(e -> e, e -> (ChronoUnit.MONTHS.between(e.getHireDate(), Instant.now()) - 6)  * coef ));
        }


    }

}
