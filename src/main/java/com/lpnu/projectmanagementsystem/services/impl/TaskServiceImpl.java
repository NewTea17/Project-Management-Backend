package com.lpnu.projectmanagementsystem.services.impl;

import com.lpnu.projectmanagementsystem.entities.ProjectEntity;
import com.lpnu.projectmanagementsystem.entities.TaskEntity;
import com.lpnu.projectmanagementsystem.entities.UserEntity;
import com.lpnu.projectmanagementsystem.repositories.TaskRepository;
import com.lpnu.projectmanagementsystem.requests.TaskRequest;
import com.lpnu.projectmanagementsystem.services.ProjectService;
import com.lpnu.projectmanagementsystem.services.TaskService;
import com.lpnu.projectmanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Override
    public TaskEntity getTaskById(Long id) throws Exception {
        Optional<TaskEntity> task = taskRepository.findById(id);
        if (task.isPresent()) {
            return task.get();
        }

        throw new Exception("Task not found");
    }

    @Override
    public List<TaskEntity> getTasksByProjectId(Long projectId) throws Exception {
        return taskRepository.findTasksByProjectId(projectId);
    }

    @Override
    public TaskEntity createTask(TaskRequest task, UserEntity user) throws Exception {
        ProjectEntity project = projectService.getProjectById(task.getProjectId());

        TaskEntity createdTask = new TaskEntity();
        createdTask.setTitle(task.getTitle());
        createdTask.setDescription(task.getDescription());
        createdTask.setStatus(task.getStatus());
        createdTask.setProjectID(task.getProjectId());
        createdTask.setPriority(task.getPriority());
        createdTask.setDueDate(task.getDueDate());

        createdTask.setProject(project);

        return taskRepository.save(createdTask);
    }

    @Override
    public void deleteTask(Long taskId, Long userId) throws Exception {
        taskRepository.deleteById(taskId);
    }

    @Override
    public TaskEntity addUserToTask(Long userId, Long taskId) throws Exception {
        UserEntity user = userService.findUserById(userId);
        TaskEntity task = getTaskById(taskId);

        task.setAssignee(user);

        return taskRepository.save(task);
    }

    @Override
    public TaskEntity updateStatus(Long taskId, String status) throws Exception {
        TaskEntity task = getTaskById(taskId);
        task.setStatus(status);
        return taskRepository.save(task);
    }
}
