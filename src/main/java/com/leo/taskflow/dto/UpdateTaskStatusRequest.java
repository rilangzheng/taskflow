package com.leo.taskflow.dto;

import com.leo.taskflow.entity.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskStatusRequest(
        @NotNull(message = "任务状态不能为空")
        TaskStatus status
) {
}