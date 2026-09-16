package com.leo.taskflow.service;

import com.leo.taskflow.dto.CreateTaskRequest;
import com.leo.taskflow.dto.TaskResponse;
import com.leo.taskflow.entity.Task;
import com.leo.taskflow.dto.UpdateTaskRequest;
import com.leo.taskflow.dto.UpdateTaskStatusRequest;
import com.leo.taskflow.entity.TaskStatus;
import com.leo.taskflow.mapper.TaskMapper;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final TaskMapper taskMapper;

    public TaskService(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public TaskResponse createTask(CreateTaskRequest request) {
        Task task = new Task(
                null,
                request.title(),
                request.description(),
                TaskStatus.TODO
        );

        taskMapper.insert(task);

        return toResponse(task);
    }

    public List<TaskResponse> getTasks() {
        List<TaskResponse> responses = new ArrayList<>();

        for (Task task : taskMapper.findAll()) {
            responses.add(toResponse(task));
        }

        return responses;
    }

    public TaskResponse getTaskById(Long id) {
        Task task = taskMapper.findById(id);

        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "任务不存在，id：" + id);
        }

        return toResponse(task);
    }

    public void deleteTaskById(Long id) {
        int affectedRows = taskMapper.deleteById(id);

        if (affectedRows == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "任务不存在，id：" + id);
        }
    }

    public TaskResponse updateTaskById(Long id, UpdateTaskRequest request) {
        int affectedRows = taskMapper.updateTitle(id, request.title());

        if (affectedRows == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "任务不存在，id：" + id);
        }

        Task updatedTask = taskMapper.findById(id);

        return toResponse(updatedTask);
    }

    public TaskResponse updateTaskStatusById(Long id, UpdateTaskStatusRequest request) {
        int affectedRows = taskMapper.updateStatus(id, request.status());

        if (affectedRows == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "任务不存在，id：" + id);
        }

        Task updatedTask = taskMapper.findById(id);

        return toResponse(updatedTask);
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.id(),
                task.title(),
                task.description(),
                task.status()
        );
    }
}
