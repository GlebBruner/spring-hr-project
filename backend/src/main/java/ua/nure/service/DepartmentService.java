package ua.nure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.domain.Department;
import ua.nure.repository.DepartmentRepository;

import java.util.List;


@Service
public class DepartmentService {

    private DepartmentRepository departmentRepository;

    @Autowired
    public void setDepartmentRepository(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> findAll () {
        return this.departmentRepository.findAll();
    }

    public Department findOne(Integer id) {
        return this.departmentRepository.findOne(id);
    }

    public Long create(Department department) {
        return this.departmentRepository.save(department);
    }

    public void delete(Integer id) {
        this.departmentRepository.delete(id);
    }

    public void update (Department department) {
        this.departmentRepository.update(department);
    }

}
