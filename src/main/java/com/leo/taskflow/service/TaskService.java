package com.leo.taskflow.service;

import com.leo.taskflow.dto.CreateTaskRequest;
import com.leo.taskflow.dto.TaskResponse;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    public TaskResponse createTask(CreateTaskRequest request) {
        return new TaskResponse("任务创建成功", request.title());
    }
}
