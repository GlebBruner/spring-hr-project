package ua.nure.repository.jpa;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.domain.Job;
import ua.nure.repository.JobRepository;

import java.util.List;

@Repository
@Profile("jpa")
@Primary
@Transactional
public class JobRepositoryHibernate implements JobRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override

    public Long save(Job job) {
        return (Long)sessionFactory.getCurrentSession().save(job);
    }

    @Override
    @Transactional(readOnly = true)
    public Job findOne(Integer id) {
        return sessionFactory.getCurrentSession().get(Job.class, id);
    }

    @Override
    @Transactional(readOnly = true)
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
