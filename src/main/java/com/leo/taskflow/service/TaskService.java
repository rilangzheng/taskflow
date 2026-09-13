package com.leo.taskflow.service;

import com.leo.taskflow.dto.CreateTaskRequest;
import com.leo.taskflow.dto.TaskResponse;
import com.leo.taskflow.entity.Task;
import com.leo.taskflow.dto.UpdateTaskRequest;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

@Service
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private long nextId = 1L;

    public TaskResponse createTask(CreateTaskRequest request) {
        Task task = new Task(nextId, request.title());

        nextId++;
        tasks.add(task);

        return toResponse(task);
    }

    public List<TaskResponse> getTasks() {
        List<TaskResponse> responses = new ArrayList<>();

        for (Task task : tasks) {
            responses.add(toResponse(task));
        }

        return responses;
    }

    public TaskResponse getTaskById(Long id) {
        for (Task task : tasks) {
            if (task.id().equals(id)) {
                return toResponse(task);
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "任务不存在，id：" + id);
    }

    public void deleteTaskById(Long id) {
        Iterator<Task> iterator = tasks.iterator();

        while (iterator.hasNext()) {
            Task task = iterator.next();

            if (task.id().equals(id)) {
                iterator.remove();
                return;
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "任务不存在，id：" + id);
    }

    public TaskResponse updateTaskById(Long id, UpdateTaskRequest request) {
        for (int index = 0; index < tasks.size(); index++) {
            Task task = tasks.get(index);

            if (task.id().equals(id)) {
                Task updatedTask = new Task(task.id(), request.title());

                tasks.set(index, updatedTask);

                return toResponse(updatedTask);
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "任务不存在，id：" + id);
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(task.id(), task.title());
    }
}
