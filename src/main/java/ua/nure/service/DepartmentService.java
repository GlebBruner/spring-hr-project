package ua.nure.service;

import ua.nure.domain.Department;
import ua.nure.repository.DepartmentRepository;

import java.util.List;


public class DepartmentService {

    private DepartmentRepository departmentRepository;

    public void setDepartmentRepository(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> findAll () {
        return this.departmentRepository.findAll();
    }

    public Department findOne(Integer id) {
        return this.departmentRepository.findOne(id);
    }


    public void create (Department department) {
        this.departmentRepository.save(department);
    }

    public void delete(Integer id) {
        this.departmentRepository.delete(id);
    }

    public boolean isDepartmentExists (Department department) {
        return this.departmentRepository.findOne(department.getId().intValue()) != null;
    }

}
