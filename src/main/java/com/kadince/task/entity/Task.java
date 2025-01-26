package com.kadince.task.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entity class representing a task.
 */
@Entity
@Table(name = "tasks")
@Setter
@Getter
public class Task {
    /**
     * The unique ID of the task.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title of the task.
     */
    @NotBlank(message = "Title is mandatory")
    private String title;

    /**
     * The description of the task.
     */
    private String description;

    /**
     * The completion status of the task.
     */
    private boolean completed;

    /**
     * The deadline of the task.
     */
    private LocalDate deadline;
}
