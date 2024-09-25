package com.lpnu.projectmanagementsystem.services.impl;

import com.lpnu.projectmanagementsystem.entities.ChatEntity;
import com.lpnu.projectmanagementsystem.entities.ProjectEntity;
import com.lpnu.projectmanagementsystem.entities.UserEntity;
import com.lpnu.projectmanagementsystem.repositories.ProjectRepository;
import com.lpnu.projectmanagementsystem.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public ProjectEntity createProject(ProjectEntity project, UserEntity user) throws Exception {
        return null;
    }

    @Override
    public List<ProjectEntity> getProjectByTeam(UserEntity user, String category, String tag) throws Exception {
        return List.of();
    }

    @Override
    public ProjectEntity getProjectById(Long id) throws Exception {
        return null;
    }

    @Override
    public void deleteProject(Long id, Long userId) throws Exception {

    }

    @Override
    public ProjectEntity updateProject(ProjectEntity updatedProject, Long id) throws Exception {
        return null;
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {

    }

    @Override
    public void deleteUserFromProject(Long projectId, Long userId) throws Exception {

    }

    @Override
    public ChatEntity getChatByProjectId(Long projectId) throws Exception {
        return null;
    }
}
