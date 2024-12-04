package com.pt.interviewms.models.entity;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Entity
@Table(name="Question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(name = "bodyQuestion", nullable = false, unique = true)
    private String bodyQuestion;

    @Column(name="interviewId")
    private Long interviewId;

    @Column(name="userId")
    private Long userId;

    @Column(name="answerUser")
    private String answerUser;

    @Column(name="answerLLM")
    private String answerLLM;

    @Column(name="score")
    private String score;

    private static Logger logger = LoggerFactory.getLogger(Question.class);

    public Question() {
    }

    public Question(String bodyQuestion, String answerLLM, Long userId) {
        this.bodyQuestion = bodyQuestion;
        this.answerLLM = answerLLM;
        this.userId = userId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getBodyQuestion() {
        return bodyQuestion;
    }

    public void setBodyQuestion(String bodyQuestion) {
        this.bodyQuestion = bodyQuestion;
    }

    public Long getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Long interviewId) {
        this.interviewId = interviewId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAnswerUser() {
        return answerUser;
    }

    public void setAnswerUser(String answerUser) {
        this.answerUser = answerUser;
    }

    public String getAnswerLLM() {
        return answerLLM;
    }

    public void setAnswerLLM(String answerLLM) {
        this.answerLLM = answerLLM;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
