package com.lpnu.projectmanagementsystem.controllers;

import com.lpnu.projectmanagementsystem.entities.ProjectEntity;
import com.lpnu.projectmanagementsystem.entities.UserEntity;
import com.lpnu.projectmanagementsystem.responses.MessageResponse;
import com.lpnu.projectmanagementsystem.services.ProjectService;
import com.lpnu.projectmanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<ProjectEntity>> getProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserEntity user = userService.findUserProfileByJwt(jwt);
        List<ProjectEntity> projects = projectService.getProjectByTeam(user, category, tag);

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProjectEntity> getProjectById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProjectEntity> createProject(
            @RequestBody ProjectEntity project,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserEntity user = userService.findUserProfileByJwt(jwt);
        ProjectEntity createdProject = projectService.createProject(project, user);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    public ResponseEntity<ProjectEntity> updateProject(
            @PathVariable Long id,
            @RequestBody ProjectEntity project
    ) throws Exception {
        ProjectEntity updatedProject = projectService.updateProject(project, id);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> deleteProject(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserEntity user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(id, user.getId());
        return new ResponseEntity<>(new MessageResponse("Project successfully deleted"), HttpStatus.OK);
    }
}
