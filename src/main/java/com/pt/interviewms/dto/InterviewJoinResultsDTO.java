package com.pt.interviewms.dto;

import java.util.List;

public class InterviewJoinResultsDTO {
    private Long interviewId;
    private List<ResultDTO> resultDTOs;

    public InterviewJoinResultsDTO() {
    }

    public Long getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Long interviewId) {
        this.interviewId = interviewId;
    }

    public List<ResultDTO> getResultDTOs() {
        return resultDTOs;
    }

    public void setResultDTOs(List<ResultDTO> resultDTOs) {
        this.resultDTOs = resultDTOs;
    }
}
