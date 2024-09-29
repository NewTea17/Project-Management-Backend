package com.lpnu.projectmanagementsystem.services.impl;

import com.lpnu.projectmanagementsystem.entities.InvitationEntity;
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

        invitationRepository.save((invitation));

        String invLink = "http://localhost/accept_invitation?token=" + token;
        emailService.sendEmailWithToken(token, invLink);
    }

    @Override
    public InvitationEntity acceptInvitation(String token, Long userId) throws Exception {
        InvitationEntity invitation = invitationRepository.findByToken(token);
        if (invitation == null) {
            throw new Exception("Invalid invitation link");
        }

        return invitation;
    }

    @Override
    public String getTokenByUserMail(String userEmail) {
        InvitationEntity invitation = invitationRepository.findByEmail(userEmail);
        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) {
        InvitationEntity invitation = invitationRepository.findByToken(token);
        invitationRepository.delete(invitation);
    }
}
