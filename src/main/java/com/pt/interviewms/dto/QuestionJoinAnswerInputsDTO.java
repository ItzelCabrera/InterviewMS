package com.pt.interviewms.dto;

import java.util.ArrayList;
import java.util.List;

public class QuestionJoinAnswerInputsDTO {
    private Long userId;
    private Long interviewId;
    private List<QuestionJoinAnswerInputDTO> qJaIsDTOs;

    public QuestionJoinAnswerInputsDTO() {
        this.qJaIsDTOs = new ArrayList<>();
    }

    public List<QuestionJoinAnswerInputDTO> getqJaIsDTOs() {
        return qJaIsDTOs;
    }

    public void setqJaIsDTOs(List<QuestionJoinAnswerInputDTO> qJaIsDTOs) {
        this.qJaIsDTOs = qJaIsDTOs;
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
}
