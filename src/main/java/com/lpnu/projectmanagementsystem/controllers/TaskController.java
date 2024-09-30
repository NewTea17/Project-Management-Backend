package com.lpnu.projectmanagementsystem.controllers;

import com.lpnu.projectmanagementsystem.entities.TaskEntity;
import com.lpnu.projectmanagementsystem.entities.UserEntity;
import com.lpnu.projectmanagementsystem.requests.TaskRequest;
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
    public ResponseEntity<TaskEntity> createTask(@RequestBody TaskRequest request, @RequestHeader("Authorization") String jwt)
            throws Exception {
        UserEntity user = userService.findUserProfileByJwt(jwt);
        TaskEntity createdTask = taskService.createTask(request, user);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }
}
