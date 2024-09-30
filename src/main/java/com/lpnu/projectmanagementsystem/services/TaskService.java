package com.lpnu.projectmanagementsystem.services;

import com.lpnu.projectmanagementsystem.entities.TaskEntity;
import com.lpnu.projectmanagementsystem.entities.UserEntity;
import com.lpnu.projectmanagementsystem.requests.TaskRequest;

import java.util.List;

public interface TaskService {
    TaskEntity getTaskById(Long id) throws Exception;
    List<TaskEntity> getTasksByProjectId(Long projectId) throws Exception;

    TaskEntity createTask(TaskRequest task, UserEntity user) throws Exception;

    void deleteTask(Long taskId, Long userId) throws Exception;

    TaskEntity addUserToTask(Long userId, Long taskId) throws Exception;

    TaskEntity updateStatus(Long taskId, String status) throws Exception;
}
