package com.pt.interviewms.dto;

public class ResultDTO {
    private String bodyQuestion;
    private String answerUser;
    private String score;

    private String answerLLM;

    public ResultDTO() {
    }

    public String getBodyQuestion() {
        return bodyQuestion;
    }

    public void setBodyQuestion(String bodyQuestion) {
        this.bodyQuestion = bodyQuestion;
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

    public String getAnswerLLM() {
        return answerLLM;
    }

    public void setAnswerLLM(String answerLLM) {
        this.answerLLM = answerLLM;
    }
}
