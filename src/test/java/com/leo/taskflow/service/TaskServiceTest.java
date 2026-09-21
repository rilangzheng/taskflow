package com.leo.taskflow.service;

import com.leo.taskflow.dto.CreateTaskRequest;
import com.leo.taskflow.dto.TaskResponse;
import com.leo.taskflow.entity.Priority;
import com.leo.taskflow.entity.Task;
import com.leo.taskflow.entity.TaskStatus;
import com.leo.taskflow.mapper.TaskMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTaskWithoutPriorityUsesMediumAndTodo() {
        // Arrange
        when(taskMapper.insert(any(Task.class))).thenAnswer(invocation -> {
            Task task = invocation.getArgument(0);
            task.setId(1L);
            return 1;
        });

        CreateTaskRequest request = new CreateTaskRequest(
                "学习 Mockito",
                null,
                null,
                null
        );

        // Act
        TaskResponse response = taskService.createTask(request);

        // Assert
        assertEquals(1L, response.id());
        assertEquals("学习 Mockito", response.title());
        assertNull(response.description());
        assertEquals(Priority.MEDIUM, response.priority());
        assertNull(response.dueDate());
        assertEquals(TaskStatus.TODO, response.status());

        verify(taskMapper).insert(any(Task.class));
    }

    @Test
    void getTaskByIdWhenTaskDoesNotExistThrowsNotFound() {
        // Arrange
        Long taskId = 999L;
        when(taskMapper.findById(taskId)).thenReturn(null);

        // Act
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> taskService.getTaskById(taskId)
        );

        // Assert
        assertEquals(404, exception.getStatusCode().value());
        assertEquals("任务不存在，id：999", exception.getReason());

        verify(taskMapper).findById(taskId);
    }
}