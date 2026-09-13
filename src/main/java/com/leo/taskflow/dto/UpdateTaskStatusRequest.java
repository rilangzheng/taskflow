package com.leo.taskflow.dto;

import com.leo.taskflow.entity.TaskStatus;

public record UpdateTaskStatusRequest(TaskStatus status) {
}
