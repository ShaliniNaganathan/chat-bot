package com.example.chatgpt.service;


import com.example.chatgpt.model.Chat;
import com.example.chatgpt.model.Demo;
import com.example.chatgpt.repository.ChatRepository;
import com.example.chatgpt.repository.SaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class ChatService {

    public ChatService()
    {

    }

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private SaveRepository saveRepository;


    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    private static final String HASH_ALGORITHM = "SHA-256";

    private Map<String, String> userSessionMap = new HashMap<>();


    public String getAnswerByQuestion(String question) {

        String[] words = question.split("\\s+");
        List<Chat> answerList = new ArrayList<>();
        for (String word : words) {
            List<Chat> matchingChats = chatRepository.findAnswerByQuestionContainingIgnoreCase(word);
            answerList.addAll(matchingChats);
        }

        if (answerList.isEmpty()) {
            return "Sorry, I don't have an answer to that question.";
        }
        else {
            int maxMatchingWords = 0;
            Chat ans = null;
            for (Chat chat : answerList) {
                int matchingWords = 0;
                for (String word : words) {
                    if (chat.getQuestion().toLowerCase().contains(word.toLowerCase())){
                        matchingWords++;
                    }
                }
                if (matchingWords > maxMatchingWords) {
                    maxMatchingWords = matchingWords;
                    ans = chat;
                }


            }
            return ans.getAnswer();
         }



 }

    private String generateSessionId(String userId) {
        String sessionId = userSessionMap.get(userId);
        if (sessionId == null) {
            sessionId = generateNewSessionId(userId);
            userSessionMap.put(userId, sessionId);
        }
        return sessionId;
    }


    private String generateNewSessionId(String userId) {
        long timestamp = System.currentTimeMillis();
        String input = userId + "_" + timestamp;
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate session ID", e);
        }
    }

    public Demo saveDemo(Demo demo) {
        String userId = "testuser";
        demo.setUserId(userId);



        if (demo.getSessionId() == null) {
            demo.setCreatedDate(LocalDateTime.now());
        }

        String sessionId = generateSessionId(userId);
        demo.setSessionId(sessionId);


        demo.setUpdatedDate(LocalDateTime.now());

        return saveRepository.save(demo);
    }


    public List<Demo> getById(int id) {
        return saveRepository.findById(id);

    }

    public List<Demo> getAllChats() {
        return saveRepository.findAll();
    }







}











