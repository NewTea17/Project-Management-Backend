package com.lpnu.projectmanagementsystem.services;

import com.lpnu.projectmanagementsystem.entities.MessageEntity;

import java.util.List;

public interface MessageService {
    MessageEntity sendMessage(Long userId, Long projectId, String content) throws Exception;
    List<MessageEntity> getMessagesByProjectId(Long projectId) throws Exception;
}
