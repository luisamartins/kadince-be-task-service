package com.kadince.task;

import com.kadince.task.entity.Task;
import com.kadince.task.repository.TaskRepository;
import com.kadince.task.service.TaskService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for TaskService.
 */
@SpringBootTest
@ActiveProfiles("test")
public class TaskServiceTest {
    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    /**
     * Test for getting all tasks ordered by deadline and completion status.
     */
    @Test
    public void testGetAllTasksOrdered() {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");
        task1.setCompleted(false);
        task1.setDeadline(LocalDate.of(2025, 1, 30));

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");
        task2.setCompleted(true);
        task2.setDeadline(LocalDate.of(2025, 2, 1));

        when(taskRepository.findAllOrderByDeadlineAndCompleted()).thenReturn(Arrays.asList(task1, task2));

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(2, tasks.size());
        assertEquals("Task 1", tasks.get(0).getTitle());
        assertEquals("Task 2", tasks.get(1).getTitle());
    }

    /**
     * Test for creating a task with a title.
     */
    @Test
    public void testCreateTaskWithTitle() {
        Task task = new Task();
        task.setTitle("New Task");
        task.setDescription("A valid task description");
        task.setCompleted(false);
        task.setDeadline(LocalDate.of(2025, 1, 30));

        when(taskRepository.save(task)).thenReturn(task);

        Task createdTask = taskService.createTask(task);

        assertNotNull(createdTask);
        assertEquals("New Task", createdTask.getTitle());
        assertEquals("A valid task description", createdTask.getDescription());
        assertFalse(createdTask.isCompleted());
        assertEquals(LocalDate.of(2025, 1, 30), createdTask.getDeadline());

        verify(taskRepository, times(1)).save(task);
    }

    /**
     * Test for creating a task without a title.
     */
    @Test
    public void testCreateTaskWithoutTitle() {
        Task task = new Task();
        task.setDescription("Task without title");
        task.setCompleted(false);

        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            taskService.createTask(task);
        });

        assertTrue(exception.getMessage().contains("Title is mandatory"));
    }

    /**
     * Test for updating a task with a title.
     */
    @Test
    public void testUpdateTaskWithTitle() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Existing Task");
        existingTask.setDescription("Existing description");
        existingTask.setCompleted(false);
        existingTask.setDeadline(LocalDate.of(2025, 1, 25));

        Task updatedTaskDetails = new Task();
        updatedTaskDetails.setTitle("Updated Task");
        updatedTaskDetails.setDescription("Updated description");
        updatedTaskDetails.setCompleted(true);
        updatedTaskDetails.setDeadline(LocalDate.of(2025, 2, 15));

        when(taskRepository.getById(1L)).thenReturn(existingTask);
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        Task updatedTask = taskService.updateTask(1L, updatedTaskDetails);

        assertNotNull(updatedTask);
        assertEquals("Updated Task", updatedTask.getTitle());
        assertEquals("Updated description", updatedTask.getDescription());
        assertTrue(updatedTask.isCompleted());
        assertEquals(LocalDate.of(2025, 2, 15), updatedTask.getDeadline());

        verify(taskRepository, times(1)).getById(1L);
        verify(taskRepository, times(1)).save(existingTask);
    }

    /**
     * Test for updating a task without a title.
     */
    @Test
    public void testUpdateTaskWithoutTitle() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Existing Task");
        existingTask.setDescription("Task description");
        existingTask.setCompleted(false);

        Task updatedTask = new Task();
        updatedTask.setTitle("");
        updatedTask.setDescription("Updated description");

        when(taskRepository.getById(1L)).thenReturn(existingTask);

        Exception exception = assertThrows(ConstraintViolationException.class, () -> {
            taskService.updateTask(1L, updatedTask);
        });

        assertTrue(exception.getMessage().contains("Title is mandatory"));
    }

    /**
     * Test for deleting a task.
     */
    @Test
    public void testDeleteTask() {
        Long taskId = 1L;

        doNothing().when(taskRepository).deleteById(taskId);

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }
}
