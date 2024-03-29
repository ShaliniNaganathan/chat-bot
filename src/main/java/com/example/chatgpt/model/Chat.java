package com.example.chatgpt.model;

import javax.persistence.*;

@Entity
@Table(name="gpt")
public class Chat {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="question")
    private String question;
    @Column(name="answer")
    private String answer;

    public Chat()
    {

    }

    public Chat(int id, String question, String answer)
    {
        super();
        this.id=id;
        this.question=question;
        this.answer=answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
