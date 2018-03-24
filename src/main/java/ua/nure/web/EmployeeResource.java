package ua.nure.web;

import org.springframework.web.bind.annotation.RestController;
import ua.nure.service.EmployeeService;

@RestController
public class EmployeeResource {

    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


}
