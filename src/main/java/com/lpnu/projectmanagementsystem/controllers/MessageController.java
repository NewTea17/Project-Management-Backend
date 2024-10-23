package com.lpnu.projectmanagementsystem.controllers;

import com.lpnu.projectmanagementsystem.entities.ChatEntity;
import com.lpnu.projectmanagementsystem.entities.MessageEntity;
import com.lpnu.projectmanagementsystem.entities.UserEntity;
import com.lpnu.projectmanagementsystem.requests.MessageRequest;
import com.lpnu.projectmanagementsystem.services.MessageService;
import com.lpnu.projectmanagementsystem.services.ProjectService;
import com.lpnu.projectmanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("send")
    public ResponseEntity<MessageEntity> sendMessage(@RequestBody MessageRequest request) {
        try {
            MessageEntity sentMessage = messageService.sendMessage(request.getSenderId(),
                    request.getProjectId(), request.getContent());

            return new ResponseEntity<>(sentMessage, HttpStatus.CREATED);
        } catch (Exception e) {
            MessageEntity errMsg = new MessageEntity();
            errMsg.setContent(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errMsg);
        }
    }

    @GetMapping("chat/{projectId}")
    public ResponseEntity<List<MessageEntity>> getMessagesByProjectId(@PathVariable Long projectId) {
        try {
            return new ResponseEntity<>(messageService.getMessagesByProjectId(projectId), HttpStatus.OK);
        } catch (Exception e) {
            MessageEntity errMsg = new MessageEntity();
            errMsg.setContent(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(errMsg));
        }
    }
}
