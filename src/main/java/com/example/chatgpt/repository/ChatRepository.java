package com.example.chatgpt.repository;

import com.example.chatgpt.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    List<Chat> findAnswerByQuestionContainingIgnoreCase(String word);
}

