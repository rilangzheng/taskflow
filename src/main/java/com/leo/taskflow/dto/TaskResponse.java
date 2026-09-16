package com.leo.taskflow.dto;

import com.leo.taskflow.entity.TaskStatus;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status
) {
}
