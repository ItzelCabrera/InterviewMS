package com.pt.interviewms.dto;

public class CvJoinQuestionsDTO {
    private Long cvId;
    private String questions;

    public CvJoinQuestionsDTO() {

    }

    public CvJoinQuestionsDTO(Long cvId, String questions) {
        this.cvId = cvId;
        this.questions = questions;
    }

    public Long getCvId() {
        return cvId;
    }

    public void setCvId(Long cvId) {
        this.cvId = cvId;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }
}
