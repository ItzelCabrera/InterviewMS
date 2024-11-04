package com.pt.interviewms.dto;

import java.util.Date;

public class InterviewRecordDTO {
    private Long interviewId;
    private Date dateTime;

    private Long score;

    public InterviewRecordDTO() {
    }

    public InterviewRecordDTO(Long interviewId, Date dateTime, Long score) {
        this.interviewId = interviewId;
        this.dateTime = dateTime;
        this.score = score;
    }

    public Long getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Long interviewId) {
        this.interviewId = interviewId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
