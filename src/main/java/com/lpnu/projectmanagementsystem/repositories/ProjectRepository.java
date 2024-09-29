package com.lpnu.projectmanagementsystem.repositories;

import com.lpnu.projectmanagementsystem.entities.ProjectEntity;
import com.lpnu.projectmanagementsystem.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
//    List<ProjectEntity> findByOwner(UserEntity user);
    List<ProjectEntity> findByNameContainingAndTeamContains(String partialName, UserEntity user);

//    @Query("SELECT p FROM ProjectEntity p join p.team t where t=:user")
//    List<ProjectEntity> findProjectByTeam(@Param("user") UserEntity user);

    List<ProjectEntity> findByTeamContainingOrOwner(UserEntity user, UserEntity owner);
}
