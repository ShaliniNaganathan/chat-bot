package com.example.chatgpt.repository;

import com.example.chatgpt.model.Demo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaveRepository extends JpaRepository<Demo, Integer> {
    List<Demo> findById(int id);
}
