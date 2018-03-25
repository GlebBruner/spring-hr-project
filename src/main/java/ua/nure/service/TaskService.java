package ua.nure.service;

import ua.nure.domain.Task;
import ua.nure.repository.TaskRepository;

import java.util.List;

public class TaskService {

    private TaskRepository taskRepository;

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Long createTask (Task task) {
        return this.taskRepository.save(task);
    }

    public Task getTask (Integer id) {
        return this.taskRepository.findOne(id);
    }

    public void updateTask (Task task) {
        this.taskRepository.update(task);
    }

    public void deleteTask(Integer id) {
        this.taskRepository.delete(id);
    }

    public List<Task> findAllTasks () {
        return this.taskRepository.findAll();
    }
}
