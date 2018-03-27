package ua.nure.service;

import ua.nure.domain.Task;
import ua.nure.repository.TaskRepository;

import java.util.List;

public class TaskService {

    private TaskRepository taskRepository;

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task findOne(Integer id) {
        return this.taskRepository.findOne(id);
    }

    public List<Task> findAll() {
        return this.taskRepository.findAll();
    }

    public Long create(Task task) {
        return this.taskRepository.save(task);
    }

    public void update(Task task) {
        this.taskRepository.update(task);
    }

    public void delete(Integer id) {
        this.taskRepository.delete(id);
    }
}
