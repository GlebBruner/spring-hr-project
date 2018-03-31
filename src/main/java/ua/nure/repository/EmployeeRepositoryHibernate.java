package ua.nure.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.nure.domain.Employee;

import java.util.List;

@Repository
public class EmployeeRepositoryHibernate implements CrudRepository<Employee>{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long save(Employee employee) {
        return (Long) sessionFactory.getCurrentSession().save(employee);
    }

    @Override
    public Employee findOne(Integer id) {
        return sessionFactory.getCurrentSession().get(Employee.class, id);
    }

    @Override
    public List<Employee> findAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM employee").list();
    }

    @Override
    public void delete(Integer id) {
        Employee employeeForDelete = findOne(id);
        if (employeeForDelete != null) {
            sessionFactory.getCurrentSession().delete(employeeForDelete);
        }
    }

    @Override
    public void update(Employee employee) {
        Employee employeeForUpdate = findOne(employee.getId().intValue());
        if (employeeForUpdate != null) {
            employeeForUpdate.setFirstName(employee.getFirstName());
            employeeForUpdate.setLastName(employee.getLastName());
            employeeForUpdate.setDepartment(employee.getDepartment());
            employeeForUpdate.setEmail(employee.getEmail());
            employeeForUpdate.setHireDate(employee.getHireDate());
            employeeForUpdate.setSalary(employee.getSalary());
            employeeForUpdate.setPhoneNumber(employee.getPhoneNumber());
            employeeForUpdate.setManager(employee.getManager());
            employeeForUpdate.setJobs(employee.getJobs());
            sessionFactory.getCurrentSession().update(employeeForUpdate);
        }
    }
}
