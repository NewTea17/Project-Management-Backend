package com.lpnu.projectmanagementsystem.controllers;

import com.lpnu.projectmanagementsystem.entities.TaskEntity;
import com.lpnu.projectmanagementsystem.entities.UserEntity;
import com.lpnu.projectmanagementsystem.requests.TaskRequest;
import com.lpnu.projectmanagementsystem.responses.AuthResponse;
import com.lpnu.projectmanagementsystem.responses.MessageResponse;
import com.lpnu.projectmanagementsystem.responses.TaskResponse;
import com.lpnu.projectmanagementsystem.services.TaskService;
import com.lpnu.projectmanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<TaskEntity> getTaskById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    @GetMapping("project/{id}")
    public ResponseEntity<List<TaskEntity>> getTaskByProjectId(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(taskService.getTasksByProjectId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request, @RequestHeader("Authorization") String jwt)
            throws Exception {
        UserEntity user = userService.findUserProfileByJwt(jwt);

        TaskEntity createdTask = taskService.createTask(request, user);
        TaskResponse response = new TaskResponse(
                createdTask.getId(),
                createdTask.getTitle(),
                createdTask.getDescription(),
                createdTask.getStatus(),
                createdTask.getProjectID(),
                createdTask.getPriority(),
                createdTask.getDueDate(),
                createdTask.getTags(),
                createdTask.getAssignee(),
                createdTask.getProject()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteTask(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserProfileByJwt(jwt);
        taskService.deleteTask(id, user.getId());
        return new ResponseEntity<>(new MessageResponse(String.format("Task deleted by %s", user.getFullName())), HttpStatus.OK);
    }

    @PutMapping("{taskId}/assignee/{userId}")
    public ResponseEntity<TaskEntity> addUserToTask(@PathVariable Long taskId, @PathVariable Long userId) throws Exception {
        return new ResponseEntity<>(taskService.addUserToTask(userId, taskId), HttpStatus.OK);
    }

    @PutMapping("{taskId}/status/{status}")
    public ResponseEntity<TaskEntity> updateStatusOfTask(@PathVariable Long taskId, @PathVariable String status) throws Exception {
        return new ResponseEntity<>(taskService.updateStatus(taskId, status), HttpStatus.OK);
    }
}
