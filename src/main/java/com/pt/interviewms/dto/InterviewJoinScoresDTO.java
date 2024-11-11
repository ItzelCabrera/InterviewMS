package com.pt.interviewms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InterviewJoinScoresDTO {
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("interviewId")
    private Long interviewId;
    @JsonProperty("scores")
    private String scores;

    public InterviewJoinScoresDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Long interviewId) {
        this.interviewId = interviewId;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }
}
