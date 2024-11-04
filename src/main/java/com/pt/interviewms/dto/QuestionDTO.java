package com.pt.interviewms.dto;

public class QuestionDTO {
    private String bodyQuestion;

    public QuestionDTO() {
    }

    public QuestionDTO(String bodyQuestion) {
        this.bodyQuestion = bodyQuestion;
    }

    public String getBodyQuestion() {
        return bodyQuestion;
    }

    public void setBodyQuestion(String bodyQuestion) {
        this.bodyQuestion = bodyQuestion;
    }
}
