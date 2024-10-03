package com.lpnu.projectmanagementsystem.services.impl;

import com.lpnu.projectmanagementsystem.entities.CommentEntity;
import com.lpnu.projectmanagementsystem.entities.TaskEntity;
import com.lpnu.projectmanagementsystem.entities.UserEntity;
import com.lpnu.projectmanagementsystem.repositories.CommentRepository;
import com.lpnu.projectmanagementsystem.services.CommentService;
import com.lpnu.projectmanagementsystem.services.TaskService;
import com.lpnu.projectmanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Override
    public CommentEntity createComment(Long taskId, Long userId, String comment) throws Exception {
        TaskEntity task = taskService.getTaskById(taskId);
        UserEntity user = userService.findUserById(userId);

        if (user == null) {
            throw new Exception("User not found");
        }

        if (task == null) {
            throw new Exception("Task not found");
        }

        CommentEntity createdComment = new CommentEntity();
        createdComment.setContent(comment);
        createdComment.setCreatedDateTime(LocalDateTime.now());
        createdComment.setTask(task);
        createdComment.setUser(user);

        CommentEntity savedComment = commentRepository.save(createdComment);

        task.getComments().add(savedComment);

        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Optional<CommentEntity> comment = commentRepository.findById(commentId);
        UserEntity user = userService.findUserById(userId);

        if (user == null) {
            throw new Exception("User not found");
        }

        if (comment.isEmpty()) {
            throw new Exception("Comment not found");
        }

        if (comment.get().getUser().equals(user)) {
            commentRepository.delete(comment.get());
        } else {
            throw new Exception("User doesn't have permission to delete this comment!");
        }
    }

    @Override
    public List<CommentEntity> getCommentsByTaskId(Long taskId) {
        return commentRepository.findCommentsByTaskId(taskId);
    }
}
