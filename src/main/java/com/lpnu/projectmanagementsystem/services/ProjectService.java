package com.lpnu.projectmanagementsystem.services;

import com.lpnu.projectmanagementsystem.entities.ChatEntity;
import com.lpnu.projectmanagementsystem.entities.ProjectEntity;
import com.lpnu.projectmanagementsystem.entities.UserEntity;

import java.util.List;

public interface ProjectService {
    ProjectEntity createProject(ProjectEntity project, UserEntity user) throws Exception;
    List<ProjectEntity> getProjectByTeam(UserEntity user, String category, String tag) throws Exception;
    ProjectEntity getProjectById(Long id) throws Exception;
    void deleteProject(Long id, Long userId) throws Exception;
    ProjectEntity updateProject(ProjectEntity updatedProject, Long id) throws Exception;

    void addUserToProject(Long projectId, Long userId) throws Exception;
    void deleteUserFromProject(Long projectId, Long userId) throws Exception;

    ChatEntity getChatByProjectId(Long projectId) throws Exception;
}
