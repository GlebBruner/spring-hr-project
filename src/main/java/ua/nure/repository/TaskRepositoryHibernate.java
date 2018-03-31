package ua.nure.repository;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.nure.domain.Task;

import java.util.List;

@Repository
public class TaskRepositoryHibernate implements CrudRepository<Task>{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long save(Task task) {
        return (Long)sessionFactory.getCurrentSession().save(task);
    }

    @Override
    public Task findOne(Integer id) {
        return sessionFactory.getCurrentSession().get(Task.class, id);
    }

    @Override
    public List<Task> findAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM task").list();
    }

    @Override
    public void delete(Integer id) {
        Task taskForDelete = findOne(id);
        if (taskForDelete != null) {
            sessionFactory.getCurrentSession().delete(taskForDelete);
        }
    }

    @Override
    public void update(Task task) {
        Task taskForUpdate = findOne(task.getId().intValue());
        if (taskForUpdate != null) {
            taskForUpdate.setDescription(task.getDescription());
            taskForUpdate.setTitle(task.getTitle());
            taskForUpdate.setJobs(task.getJobs());
            sessionFactory.getCurrentSession().update(taskForUpdate);
        }
    }
}
