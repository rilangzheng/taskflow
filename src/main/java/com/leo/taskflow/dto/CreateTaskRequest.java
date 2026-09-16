package com.leo.taskflow.dto;

import com.leo.taskflow.entity.Priority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateTaskRequest(
        @NotBlank(message = "标题不能为空")
        @Size(max = 100, message = "标题长度不能超过 100 个字符")
        String title,

        @Size(max = 500, message = "描述长度不能超过 500 个字符")
        String description,

        Priority priority,
        LocalDate dueDate
) {
}