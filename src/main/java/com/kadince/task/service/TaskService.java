package com.kadince.task.service;

import com.kadince.task.entity.Task;
import com.kadince.task.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Service class for managing tasks.
 */
@Service
@Validated
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    /**
     * Retrieves all tasks ordered by deadline and completion status.
     *
     * @return a list of tasks
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAllOrderByDeadlineAndCompleted();
    }

    /**
     * Creates a new task.
     *
     * @param task the task to create
     * @return the created task
     */
    public Task createTask(@Valid Task task) {
        return taskRepository.save(task);
    }

    /**
     * Updates an existing task.
     *
     * @param id the ID of the task to update
     * @param newTask the new task details
     * @return the updated task
     */
    public Task updateTask(Long id, @Valid Task newTask) {
        var oldTask = taskRepository.getById(id);
        oldTask.setTitle(newTask.getTitle());
        oldTask.setDescription(newTask.getDescription());
        oldTask.setCompleted(newTask.isCompleted());
        oldTask.setDeadline(newTask.getDeadline());
        return taskRepository.save(oldTask);
    }

    /**
     * Deletes a task by ID.
     *
     * @param id the ID of the task to delete
     */
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
