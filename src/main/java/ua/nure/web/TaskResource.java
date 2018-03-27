package ua.nure.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.domain.Task;
import ua.nure.service.TaskService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskResource {

    private TaskService taskService;

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getTasks () {
        return ResponseEntity.ok(this.taskService.findAll());
    }

    @GetMapping("/tasks/{task_id}")
    public ResponseEntity<Task> getTask (@PathVariable Integer task_id) {
        return ResponseEntity.ok(this.taskService.findOne(task_id));
    }

    @PostMapping("/tasks")
    public ResponseEntity<Void> createTask (@RequestBody Task task) {
        Long createdTaskId = this.taskService.create(task);
        return ResponseEntity.created(URI.create("/api/tasks/" + createdTaskId)).build();
    }

    @DeleteMapping("/tasks/{task_id}")
    public ResponseEntity<Task> deleteTask (@PathVariable Integer task_id) {
        this.taskService.delete(task_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/tasks")
    public ResponseEntity<Void> updateTask (@RequestBody Task task) {
        Task task1 = this.taskService.findOne(task.getId().intValue());

        if (task1 == null) {
            return ResponseEntity.notFound().build();
        }
        this.taskService.update(task);
        return ResponseEntity.noContent().build();
    }
}
