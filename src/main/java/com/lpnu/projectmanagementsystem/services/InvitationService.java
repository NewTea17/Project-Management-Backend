package com.lpnu.projectmanagementsystem.services;

import com.lpnu.projectmanagementsystem.entities.InvitationEntity;
import jakarta.mail.MessagingException;

public interface InvitationService {
    void sendInvitation(String email, Long projectId) throws MessagingException;
    InvitationEntity acceptInvitation(String token, Long userId) throws Exception;
}
