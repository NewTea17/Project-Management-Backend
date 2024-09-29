package com.lpnu.projectmanagementsystem.repositories;

import com.lpnu.projectmanagementsystem.entities.InvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<InvitationEntity, Long> {
    InvitationEntity findByToken(String token);
    InvitationEntity findByEmail(String userEmail);
}
