package com.leo.taskflow.dto;

public record ApiErrorResponse(
        int status,
        String message,
        String path
) {
}