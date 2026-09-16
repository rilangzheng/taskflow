package com.leo.taskflow.dto;

import com.leo.taskflow.entity.Priority;
import com.leo.taskflow.entity.TaskStatus;

import java.time.LocalDate;

public record TaskResponse(
        Long id,
        String title,
        String description,
        Priority priority,
        LocalDate dueDate,
        TaskStatus status
) {
}
