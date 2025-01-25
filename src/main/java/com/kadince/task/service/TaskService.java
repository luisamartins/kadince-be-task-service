package com.kadince.task.service;

import com.kadince.task.entity.Task;
import com.kadince.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAllOrderByDeadlineAndCompleted();
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task newTask) {
        var oldTask = taskRepository.getById(id);
        oldTask.setTitle(newTask.getTitle());
        oldTask.setDescription(newTask.getDescription());
        oldTask.setCompleted(newTask.isCompleted());
        return taskRepository.save(oldTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
