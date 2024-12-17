package com.pt.interviewms.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pt.interviewms.dto.*;
import com.pt.interviewms.dto.InterviewRecordsDTO;
import com.pt.interviewms.models.entity.InterviewRecord;
import com.pt.interviewms.models.entity.Question;
import com.pt.interviewms.repositories.InterviewRecordRepository;
import com.pt.interviewms.repositories.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
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
    private KafkaTemplate<Integer, String> kafkaTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    //obtiene aleatoriamente de BD las preguntas y genera una entrevista
    @Override
    public InterviewJoinQuestionsDTO generaInterview(Long userId) {
    	logger.info(String.format("[%s] Start method from service for userId [%d]", this.getClass().getSimpleName(),userId));
        InterviewRecord interviewRecord = new InterviewRecord(new Date(),userId);
        InterviewRecord interviewRecordDB =  interviewRecordRepository.save(interviewRecord);
        List<Question> questions = questionRepository.selectNullQuestionsByUserId_query(userId).
                orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<Question> questionsDB = new ArrayList<>();
        logger.info(String.format("[%s] questions.size: [%d]", questions.size()));
        for(Question question:questions){
            question.setInterviewId(interviewRecordDB.getInterviewId());
            questionsDB.add(questionRepository.save(question));
        }
        InterviewJoinQuestionsDTO ijqDTO = new InterviewJoinQuestionsDTO();
        ijqDTO.setInterviewId(interviewRecordDB.getInterviewId());
        ijqDTO.setQuestionDTOs(getQuestionsDTOS(questionsDB));
        ijqDTO.getQuestionDTOs().forEach(questionDTO ->
        		logger.info(String.format("[%s] Pregunta: {}", this.getClass().getSimpleName(),questionDTO.getBodyQuestion()))
        		);
        logger.info(String.format("[%s] Completed method from service for userId [%d]", this.getClass().getSimpleName(),userId));
        return ijqDTO;
    }

    //filtra los overview de todas las entrevistas realizadas
    @Override
    public InterviewRecordsDTO filtrarOverviews(Long userId) {
    	logger.info(String.format("[%s] Start method from service for userId [%d]", this.getClass().getSimpleName(),userId));
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
        interviewRecordsDTO.getInterviewRecordDTOs().stream().forEach(ir -> logger.info(String.format("interviewId %d dateTime %s score %d", ir.getInterviewId(), ir.getDateTime().toString(), ir.getScore())));
        return interviewRecordsDTO;
    }

    //obtener una entrevista ya realizada (interviewId, question <bodyQuestion>, answer <answerUser, score>)
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

//INTERACCIÓN CON LLM
    //setea las preguntas generadas a un determinada usuario
    @KafkaListener(topics = "questionsPublishJSON", groupId = "interviewMS")
    @Override
    public void setUserQuestions(String llmResponse) {
        try {
            logger.info(String.format("[%s] Start method listener topic questionsPublishJSON", this.getClass().getSimpleName()));
            logger.info(String.format("[%s] Message received {}", this.getClass().getSimpleName(), llmResponse));
            
            CvJoinQuestionsDTO cvJoinFieldDTO = mapper.readValue(llmResponse, CvJoinQuestionsDTO.class);
            logger.info(String.format("[%s] Mapper of CvJoinQuestionsDTO %s", this.getClass().getSimpleName(), cvJoinFieldDTO.toString()));
            
            List<String> elements = new ArrayList<>(Arrays.stream(cvJoinFieldDTO.getQuestions().split("\\|")).toList());
            
            for (String element : elements) {
                try {
                    logger.info(String.format("[%s] Start update questions", this.getClass().getSimpleName()));
                    questionRepository.updateInterviewIdByUserId(cvJoinFieldDTO.getUserId());
                    logger.info(String.format("[%s] Completed update questions", this.getClass().getSimpleName()));
                    
                    List<String> pair = new ArrayList<>(Arrays.stream(element.split("~")).toList());
                    Question question = new Question(pair.get(0).trim(), pair.get(1).trim(), cvJoinFieldDTO.getUserId());
                    
                    // Intenta guardar la pregunta
                    Question questionDB = questionRepository.save(question);
                    
                    logger.info(String.format("[%s] BodyQuestion : [%s]", this.getClass().getSimpleName(), questionDB.getBodyQuestion()));
                    logger.info(String.format("[%s] AnswerLLM : [%s]", this.getClass().getSimpleName(), questionDB.getAnswerLLM()));
                    logger.info(String.format("[%s] UserId : [%d]", this.getClass().getSimpleName(), questionDB.getUserId()));
                } catch (DataIntegrityViolationException e) {
                    // Loguea la excepción pero continúa con los demás registros
                    logger.warn(String.format("[%s] Duplicate key found for question [%s]. Skipping insertion.", 
                        this.getClass().getSimpleName(), element));
                } catch (Exception e) {
                    logger.error(String.format("[%s] An error occurred while processing question [%s]", 
                        this.getClass().getSimpleName(), element));
                    logger.error("Exception: ", e);
                }
            }
            logger.info(String.format("[%s] Completed method", this.getClass().getSimpleName()));
        } catch (JsonProcessingException e) {
            logger.error(String.format("[%s] An error JsonProcessingException ", this.getClass().getSimpleName()));
            logger.error("JsonProcessingException: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al parsear la publicación de LLM-Service");
        } catch (Exception e) {
            logger.error(String.format("[%s] An error Exception ", this.getClass().getSimpleName()));
            logger.error("Exception: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    //setea las preguntas que el usuario proporciona
    @Override
    public void setUserAnswers(QuestionJoinAnswerInputsDTO questionJoinAnswerInputsDTO) {
        for(QuestionJoinAnswerInputDTO qJaI:questionJoinAnswerInputsDTO.getqJaIsDTOs()){
            Question question = questionRepository.findByQuestionId(qJaI.getQuestionId()).
                    orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            question.setAnswerUser(qJaI.getAnswerUser());
            qJaI.setAnswerLLM(question.getAnswerLLM());
            Question questionDB = questionRepository.save(question);
        }
        try {
            kafkaTemplate.send("userAnswersPublishJSON",mapper.writeValueAsString(questionJoinAnswerInputsDTO));
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error al parsear los campos del CV");
        }
    }

    //setea el score de cada pregunta de una determinada entrevista
    @KafkaListener(topics = "answersScoresPublishJSON", groupId = "interviewMS")
    @Override
    public void setAnswersScore(String llmResponse) {
        logger.info(llmResponse);
        try {
            logger.info("setAnswersScore");
            InterviewJoinScoresDTO interviewJoinScoresDTO = mapper.readValue(llmResponse, InterviewJoinScoresDTO.class);
            logger.info(interviewJoinScoresDTO.getScores());
            List<String> elements = new ArrayList<>(Arrays.stream(interviewJoinScoresDTO.getScores().split("\\|")).toList());
            int promedio = 0;
            for(String element: elements){
                List<String> items_score = new ArrayList<>(Arrays.stream(element.split(" ")).toList());
                Question question = questionRepository.findByQuestionId(Long.valueOf(items_score.get(0).trim())).
                        orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                question.setScore(items_score.get(1).trim());
                Question questionDB = questionRepository.save(question);
                if(items_score.get(1).trim().equals("CORRECTA")) promedio++;
                logger.info("answerUser " + questionDB.getAnswerUser() + "    score " + questionDB.getScore() + " promedio " + promedio);
            }
            InterviewRecord interviewRecord = interviewRecordRepository.findById(interviewJoinScoresDTO.getInterviewId()).
                    orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            interviewRecord.setScore((promedio * 100L) /10);
            InterviewRecord interviewRecordDB = interviewRecordRepository.save(interviewRecord);
            logger.info(String.format("interviewScore %d", interviewRecord.getScore()));
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error al parsear la publicacion de LLM-Service");
        }
    }

    /*@Override
    public InterviewJoinQuestionsDTO filtrarQuestions(Long userId, Long interviewId) { //obtener las preguntas de una determinada entrevista
        InterviewRecord interviewRecord =interviewRecordRepository.findByInterviewIdAndUserId(userId, interviewId).
                orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        InterviewJoinQuestionsDTO ijqDTO = new InterviewJoinQuestionsDTO();
        List<Question> questions = questionRepository.findAllByInterviewId(interviewId).
                orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        ijqDTO.setInterviewId(interviewRecord.getInterviewId());
        ijqDTO.setQuestionDTOs(getQuestionsDTOS(questions));
        return ijqDTO;
    }*/

    public List<QuestionDTO> getQuestionsDTOS(List<Question> questions){
        List<QuestionDTO> questionsDTOs = new ArrayList<>();
        for(Question question:questions){
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setBodyQuestion(question.getBodyQuestion());
            questionDTO.setQuestionId(question.getQuestionId());
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
            //setea la información de la respuesta (answerUser, score, answerLLM)
            Question questionDB = questionRepository.findByQuestionId(question.getQuestionId()).
                    orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            Question questionStored = questionRepository.save(questionDB);
            resultDTO.setAnswerUser(questionStored.getAnswerUser());
            resultDTO.setScore(questionStored.getScore());
            resultDTO.setAnswerLLM(questionStored.getAnswerLLM());
            resultsDTOs.add(resultDTO);
        }
        resultsDTOs.stream().forEach(e -> logger.info("result " + e.getAnswerUser()));
        return resultsDTOs;
    }

}
