package com.lpnu.projectmanagementsystem.repositories;

import com.lpnu.projectmanagementsystem.entities.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
}
