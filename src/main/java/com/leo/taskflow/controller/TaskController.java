package com.leo.taskflow.controller;

import com.leo.taskflow.dto.CreateTaskRequest;
import com.leo.taskflow.dto.TaskResponse;
import com.leo.taskflow.service.TaskService;
import com.leo.taskflow.dto.UpdateTaskRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;

import com.leo.taskflow.dto.UpdateTaskStatusRequest;
import org.springframework.web.bind.annotation.PatchMapping;

import com.leo.taskflow.entity.TaskStatus;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public TaskResponse createTask(@RequestBody CreateTaskRequest request) {
        return taskService.createTask(request);
    }

    @GetMapping
    public List<TaskResponse> getTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return taskService.getTasks(status, page, size);
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(
            @PathVariable Long id,
            @RequestBody UpdateTaskRequest request
    ) {
        return taskService.updateTaskById(id, request);
    }

    @PatchMapping("/{id}/status")
    public TaskResponse updateTaskStatus(
            @PathVariable Long id,
            @RequestBody UpdateTaskStatusRequest request
    ) {
        return taskService.updateTaskStatusById(id, request);
    }
}
