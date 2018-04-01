package ua.nure.repository.jpa;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ua.nure.domain.Department;
import ua.nure.repository.CrudRepository;
import ua.nure.repository.DepartmentRepository;

import java.util.List;

@Repository
@Profile("jpa")
@Primary
public class JpaDepartmentRepository implements DepartmentRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long save(Department department) {
        return (Long)sessionFactory.getCurrentSession().save(department);
    }

    @Override
    public Department findOne(Integer id) {
        return sessionFactory.getCurrentSession().get(Department.class, id);
    }

    @Override
    public List<Department> findAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM deparment").list(); // or departments...
    }

    @Override
    public void delete(Integer id) {
        Department departmentForDelete = findOne(id);
        if (departmentForDelete != null) {
            sessionFactory.getCurrentSession().delete(departmentForDelete);
        }
    }

    @Override
    public void update(Department department) {
        Department departmentForUpdate = findOne(department.getId().intValue());
        if (departmentForUpdate != null) {
            departmentForUpdate.setDepartmentName(department.getDepartmentName());
            departmentForUpdate.setLocation(department.getLocation());
            departmentForUpdate.setEmployees(department.getEmployees());
            sessionFactory.getCurrentSession().update(departmentForUpdate);
        }
    }
}
