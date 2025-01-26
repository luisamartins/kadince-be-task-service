package com.kadince.task.service;

import com.kadince.task.entity.Task;
import com.kadince.task.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAllOrderByDeadlineAndCompleted();
    }

    public Task createTask(@Valid Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, @Valid Task newTask) {
        var oldTask = taskRepository.getById(id);
        oldTask.setTitle(newTask.getTitle());
        oldTask.setDescription(newTask.getDescription());
        oldTask.setCompleted(newTask.isCompleted());
        oldTask.setDeadline(newTask.getDeadline());
        return taskRepository.save(oldTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
