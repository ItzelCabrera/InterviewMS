package com.pt.interviewms.dto;

import java.util.List;

public class InterviewJoinQuestionsDTO {
    private Long interviewId;
    private List<QuestionDTO> questionDTOs;

    public InterviewJoinQuestionsDTO() {
    }

    public Long getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Long interviewId) {
        this.interviewId = interviewId;
    }

    public List<QuestionDTO> getQuestionDTOs() {
        return questionDTOs;
    }

    public void setQuestionDTOs(List<QuestionDTO> questionDTOs) {
        this.questionDTOs = questionDTOs;
    }
}
