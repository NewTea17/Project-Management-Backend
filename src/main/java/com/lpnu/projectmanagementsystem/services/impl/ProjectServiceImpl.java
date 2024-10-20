package com.lpnu.projectmanagementsystem.services.impl;

import com.lpnu.projectmanagementsystem.entities.ChatEntity;
import com.lpnu.projectmanagementsystem.entities.ProjectEntity;
import com.lpnu.projectmanagementsystem.entities.UserEntity;
import com.lpnu.projectmanagementsystem.repositories.ProjectRepository;
import com.lpnu.projectmanagementsystem.services.ChatService;
import com.lpnu.projectmanagementsystem.services.ProjectService;
import com.lpnu.projectmanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Override
    public ProjectEntity createProject(ProjectEntity project, UserEntity user) throws Exception {
        ProjectEntity createdProject = new ProjectEntity();
        createdProject.setOwner(user);
        createdProject.setTags(project.getTags());
        createdProject.setName(project.getName());
        createdProject.setCategory(project.getCategory());
        createdProject.setDescription(project.getDescription());
        createdProject.getTeam().add(user);

        ProjectEntity savedProject = projectRepository.save(createdProject);

        ChatEntity chat = new ChatEntity();
        chat.setProject(savedProject);

        ChatEntity projectChat = chatService.createChat(chat);
        savedProject.setChat(projectChat);

        return savedProject;
    }

    @Override
    public List<ProjectEntity> getProjectByTeam(UserEntity user, String category, String tag) throws Exception {
        List<ProjectEntity> projects = projectRepository.findByTeamContainingOrOwner(user, user);

        if (category != null) {
            projects = projects.stream().filter(project -> project.getCategory().equals(category)).toList();
        }

        if (tag != null) {
            projects = projects.stream().filter(project -> project.getTags().contains(tag)).toList();
        }

        return projects;
    }

    @Override
    public ProjectEntity getProjectById(Long id) throws Exception {
        Optional<ProjectEntity> optionalProject = projectRepository.findById(id);
        if (optionalProject.isEmpty()) {
            throw new Exception("Project not found");
        }

        return optionalProject.get();
    }

    @Override
    public void deleteProject(Long id, Long userId) throws Exception {
        ProjectEntity project = getProjectById(id);

        UserEntity user = userService.findUserById(userId);
        if (!project.getOwner().getId().equals(userId)) {
            throw new Exception("User is not the owner of the project");
        }

        ChatEntity chat = project.getChat();
        if (chat != null) {
            chatService.deleteChat(chat.getId());
        }

        projectRepository.deleteById(id);
    }

    @Override
    public ProjectEntity updateProject(ProjectEntity updatedProject, Long id) throws Exception {
        ProjectEntity project = getProjectById(id);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setTags(updatedProject.getTags());

        return projectRepository.save(project);
    }

    @Override
    public List<ProjectEntity> searchProjects(String keyword, UserEntity user) throws Exception {
        return projectRepository.findByNameContainingAndTeamContains(keyword, user);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        ProjectEntity project = getProjectById(projectId);
        UserEntity user = userService.findUserById(userId);

        for (UserEntity teammate : project.getTeam()) {
            if (teammate.getId().equals(userId)) {
                return;
            }
        }

        project.getChat().getUsers().add(user);
        project.getTeam().add(user);

        projectRepository.save(project);
    }

    @Override
    public void deleteUserFromProject(Long projectId, Long userId) throws Exception {
        ProjectEntity project = getProjectById(projectId);
        UserEntity user = userService.findUserById(userId);

        if (project.getTeam().contains(user)) {
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }

        projectRepository.save(project);
    }

    @Override
    public ChatEntity getChatByProjectId(Long projectId) throws Exception {
        ProjectEntity project = getProjectById(projectId);
        return project.getChat();
    }
}
