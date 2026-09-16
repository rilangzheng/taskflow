package com.leo.taskflow.mapper;

import com.leo.taskflow.entity.Task;
import com.leo.taskflow.entity.TaskStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

@Mapper
public interface TaskMapper {

    @Select("""
            SELECT id, title, description, priority, due_date, status
            FROM task
            ORDER BY id
            """)
    List<Task> findAll();

    @Insert("""
            INSERT INTO task (
                title,
                description,
                priority,
                due_date,
                status,
                created_at,
                updated_at
            )
            VALUES (
                #{title},
                #{description},
                #{priority},
                #{dueDate},
                #{status},
                NOW(),
                NOW()
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Task task);

    @Select("""
            SELECT id, title, description, priority, due_date, status
            FROM task
            WHERE id = #{id}
            """)
    Task findById(@Param("id") Long id);

    @Update("""
            UPDATE task
            SET title = #{title},
                updated_at = NOW()
            WHERE id = #{id}
            """)
    int updateTitle(
            @Param("id") Long id,
            @Param("title") String title
    );

    @Update("""
            UPDATE task
            SET status = #{status},
                updated_at = NOW()
            WHERE id = #{id}
            """)
    int updateStatus(
            @Param("id") Long id,
            @Param("status") TaskStatus status
    );

    @Delete("""
            DELETE FROM task
            WHERE id = #{id}
            """)
    int deleteById(@Param("id") Long id);
}