package com.pt.interviewms.dto;

public class QuestionJoinAnswerOutputDTO {
    private Long questionId;

    private Long answerId;

    private String scoreAnswer;

    public QuestionJoinAnswerOutputDTO() {
    }

    public QuestionJoinAnswerOutputDTO(Long questionId, Long answerId, String scoreAnswer) {
        this.questionId = questionId;
        this.answerId = answerId;
        this.scoreAnswer = scoreAnswer;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getScoreAnswer() {
        return scoreAnswer;
    }

    public void setScoreAnswer(String scoreAnswer) {
        this.scoreAnswer = scoreAnswer;
    }
}
