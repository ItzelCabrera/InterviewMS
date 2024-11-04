package com.pt.interviewms.dto;

import com.pt.interviewms.dto.InterviewRecordDTO;

import java.util.ArrayList;
import java.util.List;

public class InterviewRecordsDTO {
    private List<InterviewRecordDTO> interviewRecordDTOs;

    public InterviewRecordsDTO() {
        this.interviewRecordDTOs = new ArrayList<>();
    }

    public List<InterviewRecordDTO> getInterviewRecordDTOs() {
        return interviewRecordDTOs;
    }

    public void setInterviewRecordDTOs(List<InterviewRecordDTO> interviewRecordDTOs) {
        this.interviewRecordDTOs = interviewRecordDTOs;
    }
}
