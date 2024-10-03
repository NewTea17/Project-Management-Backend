package com.lpnu.projectmanagementsystem.controllers;

import com.lpnu.projectmanagementsystem.entities.CommentEntity;
import com.lpnu.projectmanagementsystem.entities.UserEntity;
import com.lpnu.projectmanagementsystem.requests.CommentRequest;
import com.lpnu.projectmanagementsystem.responses.MessageResponse;
import com.lpnu.projectmanagementsystem.services.CommentService;
import com.lpnu.projectmanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentEntity> createComment(@RequestBody CommentRequest request,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(commentService.createComment(request.getTaskId(), user.getId(), request.getComment()),
                HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable Long id,
                                                         @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(id, user.getId());
        return new ResponseEntity<>(new MessageResponse("Comment successfully deleted"), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<CommentEntity>> getCommentsByTaskId(@PathVariable Long id,
                                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        List<CommentEntity> comments = commentService.getCommentsByTaskId(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
