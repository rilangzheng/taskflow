package com.leo.taskflow.dto;

import com.leo.taskflow.entity.Priority;

import java.time.LocalDate;

public record UpdateTaskRequest(
        String title,
        String description,
        Priority priority,
        LocalDate dueDate
) {
}
