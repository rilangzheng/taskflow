package com.leo.taskflow.service;

import com.leo.taskflow.dto.CreateTaskRequest;
import com.leo.taskflow.dto.TaskResponse;
import com.leo.taskflow.entity.Priority;
import com.leo.taskflow.entity.Task;
import com.leo.taskflow.entity.TaskStatus;
import com.leo.taskflow.mapper.TaskMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TaskServiceTest {

    @Test
    void createTaskWithoutPriorityUsesMediumAndTodo() {
        // Arrange：准备测试数据和依赖
        TaskService taskService = new TaskService(new FakeTaskMapper());

        CreateTaskRequest request = new CreateTaskRequest(
                "学习 JUnit",
                null,
                null,
                null
        );

        // Act：调用真实的业务方法
        TaskResponse response = taskService.createTask(request);

        // Assert：验证业务结果
        assertEquals(1L, response.id());
        assertEquals("学习 JUnit", response.title());
        assertNull(response.description());
        assertEquals(Priority.MEDIUM, response.priority());
        assertNull(response.dueDate());
        assertEquals(TaskStatus.TODO, response.status());
    }

    private static class FakeTaskMapper implements TaskMapper {

        @Override
        public int insert(Task task) {
            // 模拟 MyBatis 插入后回填数据库生成的 ID
            task.setId(1L);
            return 1;
        }

        @Override
        public List<Task> findAllPaged(int size, int offset) {
            throw new UnsupportedOperationException("本测试不应调用此方法");
        }

        @Override
        public List<Task> findByStatusPaged(
                TaskStatus status,
                int size,
                int offset
        ) {
            throw new UnsupportedOperationException("本测试不应调用此方法");
        }

        @Override
        public Task findById(Long id) {
            throw new UnsupportedOperationException("本测试不应调用此方法");
        }

        @Override
        public int updateDetails(
                Long id,
                String title,
                String description,
                Priority priority,
                LocalDate dueDate
        ) {
            throw new UnsupportedOperationException("本测试不应调用此方法");
        }

        @Override
        public int updateStatus(Long id, TaskStatus status) {
            throw new UnsupportedOperationException("本测试不应调用此方法");
        }

        @Override
        public int deleteById(Long id) {
            throw new UnsupportedOperationException("本测试不应调用此方法");
        }
    }
}