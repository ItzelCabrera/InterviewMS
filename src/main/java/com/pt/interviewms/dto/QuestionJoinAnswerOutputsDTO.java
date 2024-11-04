package com.pt.interviewms.dto;

import java.util.ArrayList;
import java.util.List;

public class QuestionJoinAnswerOutputsDTO {
    private Long interviewScore;
    private List<QuestionJoinAnswerOutputDTO> qJaOsDTOSs;

    public QuestionJoinAnswerOutputsDTO() {
        this.qJaOsDTOSs = new ArrayList<>();
    }

    public List<QuestionJoinAnswerOutputDTO> getqJaOsDTOSs() {
        return qJaOsDTOSs;
    }

    public void setqJaOsDTOSs(List<QuestionJoinAnswerOutputDTO> qJaOsDTOSs) {
        this.qJaOsDTOSs = qJaOsDTOSs;
    }

    public Long getInterviewScore() {
        return interviewScore;
    }

    public void setInterviewScore(Long interviewScore) {
        this.interviewScore = interviewScore;
    }
}
