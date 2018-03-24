package ua.nure.web;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.nure.domain.Department;
import ua.nure.domain.Employee;
import ua.nure.service.DepartmentService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class DepartmentResource {

    private DepartmentService departmentService;

    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getAllDepartments () {
        List<Department>  allDepartments = this.departmentService.findAll();
//        if (allDepartments.isEmpty()) {
//            return new ResponseEntity<List<Department>>(HttpStatus.NO_CONTENT);
//        } // fixme empty array is not equal to no content
        return ResponseEntity.ok(allDepartments);
    }

    @GetMapping("/departments/{department_id}")
    public ResponseEntity<Department> getDepartment(@PathVariable Integer department_id) {
        return ResponseEntity.ok(this.departmentService.findOne(department_id));
    }

    @PostMapping("/departments")
    public ResponseEntity<Void> createDepartment (@PathVariable Department department, UriComponentsBuilder uriBuilder) {
        if (this.departmentService.isDepartmentExists(department)) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        } else {
            Long id = this.departmentService.create(department);
            return ResponseEntity.created(URI.create("/api/departments/" + id)).build();
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.setLocation(uriBuilder.path("/departments/{department_id}").buildAndExpand(department.getId()).toUri());
//            return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/departments/{department_id}")
    public ResponseEntity<Department> deleteDepartment (@PathVariable Integer department_id) {
        if (this.departmentService.findOne(department_id) == null) {
            return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
        } else {
            this.departmentService.delete(department_id);
            return new ResponseEntity<Department>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/departments/{department_id}/employees")
    public ResponseEntity<List<Employee>> getDepartmentsEmployees(@PathVariable Integer department_id) {
        if (this.departmentService.findOne(department_id) == null) {
            return new ResponseEntity<List<Employee>>(HttpStatus.NOT_FOUND); //there's no such department
        } else {
            Set<Employee> departmentsEmployees = this.departmentService.findOne(department_id).getEmployees();
            if (departmentsEmployees.isEmpty()) {
                return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);
            } else {
                return ResponseEntity.ok(new ArrayList<>(departmentsEmployees));
            }
        }
    }
}
