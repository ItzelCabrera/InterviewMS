package com.pt.interviewms.services;

import com.pt.interviewms.dto.*;

import java.util.List;
import java.util.Optional;

public interface InterviewService {
    InterviewJoinQuestionsDTO generaInterview(Long userId);
    InterviewJoinQuestionsDTO filtrarQuestions(Long userId, Long interviewId);
    InterviewJoinResultsDTO filtrarInterview(Long userId, Long interviewId);
    InterviewRecordsDTO filtrarOverviews(Long userId);
    QuestionJoinAnswerOutputsDTO setAnswersScore(QuestionJoinAnswerInputsDTO qJaIsDTOs, Long userId, Long interviewId);

    void setUserQuestions(List<QuestionDTO> questionsDTO, Long userId);
}
