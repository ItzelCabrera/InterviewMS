package com.pt.interviewms.dto;

import java.util.ArrayList;
import java.util.List;

public class QuestionJoinAnswerInputsDTO {
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
}
