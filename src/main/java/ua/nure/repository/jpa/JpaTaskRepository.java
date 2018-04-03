package ua.nure.repository.jpa;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.domain.Task;
import ua.nure.repository.CrudRepository;
import ua.nure.repository.TaskRepository;

import java.util.List;

@Repository
@Profile("jpa")
@Primary
@Transactional
public class JpaTaskRepository implements TaskRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long save(Task task) {
        return (Long)sessionFactory.getCurrentSession().save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Task findOne(Integer id) {
        return sessionFactory.getCurrentSession().get(Task.class, id);
    }

    @Override
    @Transactional(readOnly = true)
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
