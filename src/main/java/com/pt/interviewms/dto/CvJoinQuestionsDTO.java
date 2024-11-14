package com.pt.interviewms.dto;

public class CvJoinQuestionsDTO {
    private Long userId;
    private String questions;

    public CvJoinQuestionsDTO() {

    }

    public CvJoinQuestionsDTO(Long userId, String questions) {
        this.userId = userId;
        this.questions = questions;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }
}
