package com.kadince.task.controller;

import com.kadince.task.entity.Task;
import com.kadince.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing tasks.
 */
@RestController
@RequestMapping("api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    /**
     * Retrieves all tasks.
     *
     * @return a list of tasks
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    /**
     * Creates a new task.
     *
     * @param task the task to create
     * @return the created task
     */
    @PostMapping
    public Task createTask(@Valid @RequestBody Task task) {
        return taskService.createTask(task);
    }

    /**
     * Updates an existing task.
     *
     * @param id the ID of the task to update
     * @param task the new task details
     * @return the updated task
     */
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @Valid @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    /**
     * Deletes a task by ID.
     *
     * @param id the ID of the task to delete
     * @return a response entity indicating the result
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
