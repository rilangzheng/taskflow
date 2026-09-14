package com.leo.taskflow.mapper;

import com.leo.taskflow.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface TaskMapper {

    @Select("""
            SELECT id, title, status
            FROM task
            ORDER BY id
            """)
    List<Task> findAll();

    @Insert("""
            INSERT INTO task (title, status, created_at, updated_at)
            VALUES (#{title}, #{status}, NOW(), NOW())
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Task task);
}