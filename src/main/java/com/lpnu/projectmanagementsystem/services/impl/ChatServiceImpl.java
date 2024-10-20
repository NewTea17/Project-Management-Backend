package com.lpnu.projectmanagementsystem.services.impl;

import com.lpnu.projectmanagementsystem.entities.ChatEntity;
import com.lpnu.projectmanagementsystem.repositories.ChatRepository;
import com.lpnu.projectmanagementsystem.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Override
    public ChatEntity createChat(ChatEntity chat) {
        return chatRepository.save(chat);
    }

    @Override
    public void deleteChat(Long id) {
        chatRepository.deleteById(id);
    }
}
