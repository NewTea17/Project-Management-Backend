package com.lpnu.projectmanagementsystem.controllers;

import com.lpnu.projectmanagementsystem.entities.ChatEntity;
import com.lpnu.projectmanagementsystem.entities.InvitationEntity;
import com.lpnu.projectmanagementsystem.entities.ProjectEntity;
import com.lpnu.projectmanagementsystem.entities.UserEntity;
import com.lpnu.projectmanagementsystem.requests.InvitationRequest;
import com.lpnu.projectmanagementsystem.responses.MessageResponse;
import com.lpnu.projectmanagementsystem.services.InvitationService;
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

    @Autowired
    private InvitationService invitationService;

    @GetMapping
    public ResponseEntity<List<ProjectEntity>> getProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag,
            @RequestHeader("Authorization") String jwt
    ) {
        try {
            UserEntity user = userService.findUserProfileByJwt(jwt);
            List<ProjectEntity> projects = projectService.getProjectByTeam(user, category, tag);

            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header("error", e.getMessage())
                    .body(null);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ProjectEntity> getProjectById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<ProjectEntity> createProject(
            @RequestBody ProjectEntity project,
            @RequestHeader("Authorization") String jwt
    ) {
        try {
            UserEntity user = userService.findUserProfileByJwt(jwt);
            ProjectEntity createdProject = projectService.createProject(project, user);
            return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header("error", e.getMessage())
                    .body(null);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<ProjectEntity> updateProject(
            @PathVariable Long id,
            @RequestBody ProjectEntity project
    ) {
        try {
            ProjectEntity updatedProject = projectService.updateProject(project, id);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("error", e.getMessage())
                    .body(null);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> deleteProject(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) {
        try {
            UserEntity user = userService.findUserProfileByJwt(jwt);
            projectService.deleteProject(id, user.getId());
            return new ResponseEntity<>(new MessageResponse("Project successfully deleted"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("search")
    public ResponseEntity<List<ProjectEntity>> searchProject(
            @RequestParam(required = false) String keyword,
            @RequestHeader("Authorization") String jwt
    ) {
       try {
           UserEntity user = userService.findUserProfileByJwt(jwt);
           List<ProjectEntity> projects = projectService.searchProjects(keyword, user);
           return new ResponseEntity<>(projects, HttpStatus.OK);
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("error", e.getMessage())
                   .body(null);
       }
    }

    @GetMapping("{id}/chat")
    public ResponseEntity<ChatEntity> getChatByProjectId(
            @PathVariable(required = false) Long id
    ) {
        try {
            ChatEntity chat = projectService.getChatByProjectId(id);
            return new ResponseEntity<>(chat, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header("error", e.getMessage())
                    .body(null);
        }
    }

    @PostMapping("invite")
    public ResponseEntity<MessageResponse> inviteToProject(@RequestBody InvitationRequest request) {
        try {
            invitationService.sendInvitation(request.getEmail(), request.getProjectId());
            return new ResponseEntity<>(new MessageResponse("User invited to project successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("accept_invitation")
    public ResponseEntity<InvitationEntity> acceptInviteToProject(
            @RequestParam(required = false) String token,
            @RequestHeader("Authorization") String jwt
    ) {
        try {
            UserEntity user = userService.findUserProfileByJwt(jwt);
            InvitationEntity invitation = invitationService.acceptInvitation(token, user.getId());

            projectService.addUserToProject(invitation.getProjectId(), user.getId());

            return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("error", e.getMessage())
                    .body(null);
        }
    }
}
