package com.lpnu.projectmanagementsystem.services.impl;

import com.lpnu.projectmanagementsystem.entities.ChatEntity;
import com.lpnu.projectmanagementsystem.entities.MessageEntity;
import com.lpnu.projectmanagementsystem.entities.UserEntity;
import com.lpnu.projectmanagementsystem.repositories.MessageRepository;
import com.lpnu.projectmanagementsystem.services.MessageService;
import com.lpnu.projectmanagementsystem.services.ProjectService;
import com.lpnu.projectmanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Override
    public MessageEntity sendMessage(Long userId, Long projectId, String content) throws Exception {
        UserEntity sender = userService.findUserById(userId);
        if (sender == null) {
            throw new Exception("User not found");
        }

        ChatEntity chat = projectService.getChatByProjectId(projectId);

        MessageEntity message = new MessageEntity();
        message.setChat(chat);
        message.setContent(content);
        message.setSender(sender);
        message.setCreatedAt(LocalDateTime.now());

        MessageEntity savedMessage = messageRepository.save(message);

        chat.getMessages().add(savedMessage);
        return savedMessage;
    }

    @Override
    public List<MessageEntity> getMessagesByProjectId(Long projectId) throws Exception {
        ChatEntity chat = projectService.getChatByProjectId(projectId);
        return messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
    }
}
