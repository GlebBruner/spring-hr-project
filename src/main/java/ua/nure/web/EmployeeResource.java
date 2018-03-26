package ua.nure.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.domain.Employee;
import ua.nure.service.EmployeeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeResource {

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees () {
        return ResponseEntity.ok(this.employeeService.findAll());
    }

    @GetMapping("/employees/{employee_id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Integer employee_id) {
        return ResponseEntity.ok(this.employeeService.findOne(employee_id));
    }

    @PostMapping("/employees")
    public ResponseEntity<Void> createEmployee (@RequestBody Employee employee) {
        Long id = this.employeeService.create(employee);
        return ResponseEntity.created(URI.create("/api/employees/" + id)).build();
    }

    @DeleteMapping("/employees/{employee_id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable Integer employee_id) {
        this.employeeService.delete(employee_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/employees")
    public ResponseEntity<Void> updateEmployee (@RequestBody Employee employee) {
        Employee updatedEmployee = this.employeeService.findOne(employee.getId().intValue());
        if (updatedEmployee == null) {
            return ResponseEntity.notFound().build();
        }
        this.employeeService.update(employee);
        return ResponseEntity.noContent().build();
    }
}
