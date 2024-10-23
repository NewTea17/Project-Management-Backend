package com.lpnu.projectmanagementsystem.services.impl;

import com.lpnu.projectmanagementsystem.entities.InvitationEntity;
import com.lpnu.projectmanagementsystem.exceptions.InvitationException;
import com.lpnu.projectmanagementsystem.repositories.InvitationRepository;
import com.lpnu.projectmanagementsystem.services.EmailService;
import com.lpnu.projectmanagementsystem.services.InvitationService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService {
    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void sendInvitation(String email, Long projectId) throws MessagingException {
        String token = UUID.randomUUID().toString();

        InvitationEntity invitation = new InvitationEntity();
        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.setToken(token);

        invitationRepository.save(invitation);

        String invLink = "http://localhost:5173/accept_invitation?token=" + token;
        emailService.sendEmailWithToken(email, invLink);
    }

    @Override
    public InvitationEntity acceptInvitation(String token, Long userId) throws Exception {
        InvitationEntity invitation = invitationRepository.findByToken(token);
        if (invitation == null) {
            throw new InvitationException("Invalid invitation link");
        }

        return invitation;
    }
}
