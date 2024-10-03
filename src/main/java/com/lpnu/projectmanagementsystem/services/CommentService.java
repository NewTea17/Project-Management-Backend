package com.lpnu.projectmanagementsystem.services;

import com.lpnu.projectmanagementsystem.entities.CommentEntity;

import java.util.List;

public interface CommentService {
    CommentEntity createComment(Long taskId, Long userId, String comment) throws Exception;
    void deleteComment(Long commentId, Long userId) throws Exception;
    List<CommentEntity> getCommentsByTaskId(Long taskId);
}
