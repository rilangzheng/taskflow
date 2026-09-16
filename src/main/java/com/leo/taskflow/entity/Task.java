package com.leo.taskflow.entity;

import java.time.LocalDate;

public class Task {

    private Long id;
    private String title;
    private TaskStatus status;
    private String description;
    private Priority priority;
    private LocalDate dueDate;

    public Task(
            Long id,
            String title,
            String description,
            Priority priority,
            LocalDate dueDate,
            TaskStatus status
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // 保留 record 风格的调用方式，Service 其他代码暂时不用改
    public Long id() {
        return id;
    }

    public String title() {
        return title;
    }

    public TaskStatus status() {
        return status;
    }

    public String description() {
        return description;
    }

    public Priority priority() {
        return priority;
    }

    public LocalDate dueDate() {
        return dueDate;
    }
}
