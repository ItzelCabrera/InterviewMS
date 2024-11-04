package com.pt.interviewms.services;

import com.pt.interviewms.dto.*;
import com.pt.interviewms.dto.InterviewRecordsDTO;
import com.pt.interviewms.models.entity.Answer;
import com.pt.interviewms.models.entity.InterviewRecord;
import com.pt.interviewms.models.entity.Question;
import com.pt.interviewms.repositories.AnswerRepository;
import com.pt.interviewms.repositories.InterviewRecordRepository;
import com.pt.interviewms.repositories.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InterviewServiceImpl implements InterviewService {
    private static Logger logger = LoggerFactory.getLogger(InterviewServiceImpl.class);

    @Autowired
    private InterviewRecordRepository interviewRecordRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public InterviewJoinQuestionsDTO generaInterview(Long userId) {
        InterviewRecord interviewRecord = new InterviewRecord(new Date(),userId);
        InterviewRecord interviewRecordDB =  interviewRecordRepository.save(interviewRecord);
        List<Question> questions = questionRepository.selectNullQuestionsByUserId_query(userId).
                orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<Question> questionsDB = new ArrayList<>();
        for(Question question:questions){
            question.setInterviewId(interviewRecordDB.getInterviewId());
            questionsDB.add(questionRepository.save(question));
        }
        InterviewJoinQuestionsDTO ijqDTO = new InterviewJoinQuestionsDTO();
        ijqDTO.setInterviewId(interviewRecordDB.getInterviewId());
        ijqDTO.setQuestionDTOs(getQuestionsDTOS(questionsDB));
        return ijqDTO;
    }

    @Override
    public InterviewJoinQuestionsDTO filtrarQuestions(Long userId, Long interviewId) { //obtener las preguntas de una determinada entrevista
        InterviewRecord interviewRecord =interviewRecordRepository.findByInterviewIdAndUserId(userId, interviewId).
                orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        InterviewJoinQuestionsDTO ijqDTO = new InterviewJoinQuestionsDTO();
        List<Question> questions = questionRepository.findAllByInterviewId(interviewId).
                orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        ijqDTO.setInterviewId(interviewRecord.getInterviewId());
        ijqDTO.setQuestionDTOs(getQuestionsDTOS(questions));
        return ijqDTO;
    }

    //obtener una entrevista ya realizada (question <questionId, bodyQuestion>, answer <answerId, answer, score>)
    @Override
    public InterviewJoinResultsDTO filtrarInterview(Long userId, Long interviewId) {
        InterviewRecord interviewRecord =interviewRecordRepository.findByInterviewIdAndUserId(interviewId, userId).
                orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        logger.info("interviewId" + interviewRecord.getInterviewId().toString());
        List<Question> questions = questionRepository.findAllByInterviewId(interviewRecord.getInterviewId()).
                orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        questions.stream().forEach(e-> logger.info("questionId" + e.getQuestionId().toString()));
        InterviewJoinResultsDTO ijrDTO = new InterviewJoinResultsDTO();
        ijrDTO.setInterviewId(interviewRecord.getInterviewId());
        logger.info("dto " + ijrDTO.getInterviewId());
        ijrDTO.setResultDTOs(getResultsDTOS(questions));
        return ijrDTO;
    }

    @Override
    public InterviewRecordsDTO filtrarOverviews(Long userId) {
        List<InterviewRecord> interviewRecords =interviewRecordRepository.findAllByUserId(userId).
                orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        InterviewRecordsDTO interviewRecordsDTO = new InterviewRecordsDTO();
        //List<InterviewRecordDTO> interviewRecordDTOS = new ArrayList<>();
        for(InterviewRecord interviewRecord:interviewRecords){
            InterviewRecordDTO interviewRecordDTO = new InterviewRecordDTO();
            interviewRecordDTO.setInterviewId(interviewRecord.getInterviewId());
            interviewRecordDTO.setDateTime(interviewRecord.getInterviewDate());
            interviewRecordDTO.setScore((interviewRecord.getScore()==null)?-1L:interviewRecord.getScore());
            interviewRecordsDTO.getInterviewRecordDTOs().add(interviewRecordDTO);
        }

        return interviewRecordsDTO;
    }

    @Override
    public QuestionJoinAnswerOutputsDTO setAnswersScore(QuestionJoinAnswerInputsDTO qJaIsDTOs, Long userId, Long interviewId) {
        //qJaIsDTOs.getqJaIsDTOs().stream().forEach(e-> logger.info("elemento: " + e));
        QuestionJoinAnswerOutputsDTO qJaOsDTO = new QuestionJoinAnswerOutputsDTO();
        String score = "CORRECTO";
        int promedio = 0;
        for(QuestionJoinAnswerInputDTO qJaI_ODT:qJaIsDTOs.getqJaIsDTOs()){
            //logger.info("questionId " + qJaI_ODT.getQuestionId().toString());
            if(score.equals("CORRECTO")) promedio++;
            Answer answer = new Answer(qJaI_ODT.getAnswerUser(),score);
            Answer answerDB = answerRepository.save(answer);
            //logger.info("answerUser " + answerDB.getAnswerUser() + "    score " + answerDB.getScore());
            Question question = questionRepository.findById(qJaI_ODT.getQuestionId()).
                    orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            question.setAnswerId(answerDB.getAnswerId());
            Question questionDB = questionRepository.save(question);
            //logger.info("question-answerId " + questionDB.getAnswerId());
            qJaOsDTO.getqJaOsDTOSs().add(new QuestionJoinAnswerOutputDTO(questionDB.getQuestionId(),questionDB.getAnswerId(),answerDB.getScore()));
        }
        qJaOsDTO.setInterviewScore((promedio * 100L) /2);
        InterviewRecord interviewRecord = interviewRecordRepository.findById(interviewId).
                orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        interviewRecord.setScore(qJaOsDTO.getInterviewScore());
        InterviewRecord interviewRecordDB = interviewRecordRepository.save(interviewRecord);
        //qJaOsDTO.getqJaOsDTOSs().stream().forEach(e-> logger.info("elemento: " + e));
        return qJaOsDTO;
    }

    @Override
    public void setUserQuestions(List<QuestionDTO> questionsDTOs, Long userId) {
        logger.info("public void setUserQuestions starts");
        for(QuestionDTO questionDTO: questionsDTOs){
            Question question = new Question(questionDTO.getBodyQuestion(),userId);
            Question questionDB = questionRepository.save(question);
            logger.info("bodyQuestion "+questionDB.getBodyQuestion() + "    userId " + questionDB.getUserId());
        }
        logger.info("public void setUserQuestions finished");
    }

    public List<QuestionDTO> getQuestionsDTOS(List<Question> questions){
        List<QuestionDTO> questionsDTOs = new ArrayList<>();
        for(Question question:questions){
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setBodyQuestion(question.getBodyQuestion());
            questionsDTOs.add(questionDTO);
        }
        return questionsDTOs;
    }

    public List<ResultDTO> getResultsDTOS(List<Question> questions){
        List<ResultDTO> resultsDTOs = new ArrayList<>();
        for(Question question:questions){
            ResultDTO resultDTO = new ResultDTO();
            //setea el bodyQuestion
            resultDTO.setBodyQuestion(question.getBodyQuestion());
            //setea la información de la respuesta (answerUser, score)
            Answer answer = answerRepository.findByAnswerId(question.getAnswerId()).
                    orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            resultDTO.setAnswerUser(answer.getAnswerUser());
            resultDTO.setScore(answer.getScore());
            resultsDTOs.add(resultDTO);
        }
        resultsDTOs.stream().forEach(e -> logger.info("result " + e.getAnswerUser()));
        return resultsDTOs;
    }

}
