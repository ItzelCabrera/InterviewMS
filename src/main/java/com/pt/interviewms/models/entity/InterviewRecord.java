package com.pt.interviewms.models.entity;

import com.pt.interviewms.dto.QuestionDTO;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="InterviewRecord")
public class InterviewRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interviewId;

    private Long score;

    @NotNull
    private Date interviewDate;

    @Column(name="userId")
    private Long userId;

    private static Logger logger = LoggerFactory.getLogger(InterviewRecord.class);

    public InterviewRecord() {
    }

    public InterviewRecord(Date interviewDate, Long userId) {
        this.interviewDate = interviewDate;
        this.userId = userId;
    }

    public Long getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Long interviewId) {
        this.interviewId = interviewId;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(Date interviewDate) {
        this.interviewDate = interviewDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
