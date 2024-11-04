package com.pt.interviewms.services;

import com.pt.interviewms.dto.*;

import java.util.List;
import java.util.Optional;

public interface InterviewService {
    InterviewJoinQuestionsDTO generaInterview(Long userId);
    InterviewRecordsDTO filtrarOverviews(Long userId);
    InterviewJoinResultsDTO filtrarInterview(Long userId, Long interviewId);
    void setUserAnswers(QuestionJoinAnswerInputsDTO questionJoinAnswerInputsDTO); //setea las respuestas que el usuario brinde
    void setUserQuestions(String llmResponse); //setea las preguntas al usuario
    void setAnswersScore(String llmResponse); //setea el score a las preguntas de una determinada entrevista

    //InterviewJoinQuestionsDTO filtrarQuestions(Long userId, Long interviewId);
}
