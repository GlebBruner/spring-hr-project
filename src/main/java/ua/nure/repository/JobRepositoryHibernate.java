package ua.nure.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.nure.domain.Job;

import java.util.List;

@Repository
public class JobRepositoryHibernate implements CrudRepository<Job>{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long save(Job job) {
        return (Long)sessionFactory.getCurrentSession().save(job);
    }

    @Override
    public Job findOne(Integer id) {
        return sessionFactory.getCurrentSession().get(Job.class, id);
    }

    @Override
    public List<Job> findAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM job").list();
    }

    @Override
    public void delete(Integer id) {
        Job jobForDelete = findOne(id);
        if (jobForDelete != null) {
            sessionFactory.getCurrentSession().delete(jobForDelete);
        }
    }

    @Override
    public void update(Job job) {
        Job jobForUpdate = findOne(job.getId().intValue());
        if (jobForUpdate != null) {
            jobForUpdate.setJobTitle(job.getJobTitle());
            jobForUpdate.setMinSalary(job.getMinSalary());
            jobForUpdate.setMaxSalary(job.getMaxSalary());
            jobForUpdate.setEmployee(job.getEmployee());
            jobForUpdate.setTasks(job.getTasks());
            sessionFactory.getCurrentSession().update(job);
        }
    }
}
