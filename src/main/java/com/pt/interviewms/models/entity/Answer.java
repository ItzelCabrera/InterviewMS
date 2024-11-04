package com.pt.interviewms.models.entity;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name="Answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @NotNull
    private String answerUser;

    @Column(name="score")
    private String score;

    private static Logger logger = LoggerFactory.getLogger(Answer.class);

    public Answer() {
    }

    public Answer(String answerUser) {
        this.answerUser = answerUser;
    }

    public Answer(String answerUser, String score) {
        this.answerUser = answerUser;
        this.score = score;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getAnswerUser() {
        return answerUser;
    }

    public void setAnswerUser(String answerUser) {
        this.answerUser = answerUser;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
