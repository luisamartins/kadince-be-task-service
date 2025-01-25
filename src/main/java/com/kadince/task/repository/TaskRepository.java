package com.kadince.task.repository;

import com.kadince.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t ORDER BY t.completed ASC, t.deadline ASC NULLS LAST")
    List<Task> findAllOrderByDeadlineAndCompleted();
}
