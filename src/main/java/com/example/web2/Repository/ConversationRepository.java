package com.example.web2.Repository;

import com.example.web2.Entity.Conversations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.LinkOption;

public interface ConversationRepository extends JpaRepository<Conversations, Long> {
}
