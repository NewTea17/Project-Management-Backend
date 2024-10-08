package com.lpnu.projectmanagementsystem.repositories;

import com.lpnu.projectmanagementsystem.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findCommentsByTaskId(Long taskId);
}
