package com.leo.taskflow.service;

import com.leo.taskflow.dto.CreateTaskRequest;
import com.leo.taskflow.dto.TaskResponse;
import com.leo.taskflow.entity.Task;
import com.leo.taskflow.dto.UpdateTaskRequest;
import com.leo.taskflow.dto.UpdateTaskStatusRequest;
import com.leo.taskflow.entity.TaskStatus;
import com.leo.taskflow.mapper.TaskMapper;
import com.leo.taskflow.entity.Priority;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final TaskMapper taskMapper;

    public TaskService(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public TaskResponse createTask(CreateTaskRequest request) {
        Priority priority = request.priority() == null
                ? Priority.MEDIUM
                : request.priority();

        Task task = new Task(
                null,
                request.title(),
                request.description(),
                priority,
                request.dueDate(),
                TaskStatus.TODO
        );

        taskMapper.insert(task);

        log.info(
                "任务创建成功，id={}, priority={}, status={}",
                task.id(),
                task.priority(),
                task.status()
        );

        return toResponse(task);
    }

    public List<TaskResponse> getTasks(
            TaskStatus status,
            int page,
            int size
    ) {
        if (page < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "页码不能小于 0"
            );
        }

        if (size < 1 || size > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "每页数量必须在 1 到 100 之间"
            );
        }

        int offset = page * size;

        List<Task> tasks;

        if (status == null) {
            tasks = taskMapper.findAllPaged(size, offset);
        } else {
            tasks = taskMapper.findByStatusPaged(status, size, offset);
        }

        List<TaskResponse> responses = new ArrayList<>();

        for (Task task : tasks) {
            responses.add(toResponse(task));
        }
        String statusForLog = status == null ? "ALL" : status.name();

        log.info(
                "查询任务列表成功，status={}, page={}, size={}, count={}",
                statusForLog,
                page,
                size,
                responses.size()
        );

        return responses;
    }

    public TaskResponse getTaskById(Long id) {
        Task task = taskMapper.findById(id);

        if (task == null) {
            log.warn("查询任务失败，任务不存在，id={}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "任务不存在，id：" + id);
        }

        return toResponse(task);
    }

    public void deleteTaskById(Long id) {
        int affectedRows = taskMapper.deleteById(id);

        if (affectedRows == 0) {
            log.warn("删除任务失败，任务不存在，id={}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "任务不存在，id：" + id);
        }

        log.info("任务删除成功，id={}", id);
    }

    public TaskResponse updateTaskById(Long id, UpdateTaskRequest request) {
        Priority priority = request.priority() == null
                ? Priority.MEDIUM
                : request.priority();

        int affectedRows = taskMapper.updateDetails(
                id,
                request.title(),
                request.description(),
                priority,
                request.dueDate()
        );

        if (affectedRows == 0) {
            log.warn("修改任务失败，任务不存在，id={}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "任务不存在，id：" + id);
        }

        log.info("任务基础信息更新成功，id={}, priority={}", id, priority);

        Task updatedTask = taskMapper.findById(id);

        return toResponse(updatedTask);
    }

    public TaskResponse updateTaskStatusById(Long id, UpdateTaskStatusRequest request) {
        int affectedRows = taskMapper.updateStatus(id, request.status());

        if (affectedRows == 0) {
            log.warn("更新任务状态失败，任务不存在，id={}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "任务不存在，id：" + id);
        }

        log.info("任务状态更新成功，id={}, status={}", id, request.status());

        Task updatedTask = taskMapper.findById(id);

        return toResponse(updatedTask);
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.id(),
                task.title(),
                task.description(),
                task.priority(),
                task.dueDate(),
                task.status()
        );
    }
}
