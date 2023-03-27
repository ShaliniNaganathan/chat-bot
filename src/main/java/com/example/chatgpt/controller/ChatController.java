package com.example.chatgpt.controller;

import com.example.chatgpt.model.Chat;
import com.example.chatgpt.model.Demo;
import com.example.chatgpt.model.ResponsePayload;
import com.example.chatgpt.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chat")

@CrossOrigin(origins = "*")
public class ChatController
{

    @Autowired
    private ChatService chatService;

    public ChatController(ChatService chatService)
    {
        this.chatService = chatService;
    }


    @GetMapping("/answer")
    public ResponseEntity<ResponsePayload> getAnswerByQuestion(@RequestParam("question") String question) {
        String answer = chatService.getAnswerByQuestion(question);
        ResponsePayload pay=new ResponsePayload();

        if (answer == null) {
            pay.setAnswer("Sorry, I don't have an answer to that question.");
            return new ResponseEntity<>(pay, HttpStatus.OK);
        } else {
            pay.setQuestion(question);
            pay.setAnswer(answer);
            return new ResponseEntity<>(pay, HttpStatus.OK);
        }
    }

    @PostMapping("/demo")
    public ResponseEntity<Demo> saveDemo(@RequestBody Demo demo) {
        Demo savedDemo = chatService.saveDemo(demo);
        return new ResponseEntity<>(savedDemo, HttpStatus.CREATED);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<List<Demo>> getById(@PathVariable int id)
    {
        List<Demo> demo = chatService.getById(id);
        return new ResponseEntity<>(demo, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Demo>> getAllChats()
    {
        List<Demo> demo = chatService.getAllChats();
        return new ResponseEntity<>(demo, HttpStatus.OK);
    }



}