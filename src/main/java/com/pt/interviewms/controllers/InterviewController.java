package com.pt.interviewms.controllers;

import com.pt.interviewms.dto.*;
import com.pt.interviewms.dto.response.GenericResponseDTO;
import com.pt.interviewms.models.commons.CommonController;
import com.pt.interviewms.services.InterviewService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/interview/")
public class InterviewController {
    private static Logger logger = LoggerFactory.getLogger(InterviewController.class);
    @Autowired
    private InterviewService interviewService;

    @PostMapping("/generateInterview/{userId}") //genera una entrevista con las preguntas en BD (las toma de forma aleatoria)
    public ResponseEntity<GenericResponseDTO<InterviewJoinQuestionsDTO>> generateInterview(@PathVariable Long userId){
        try{
            logger.info("Execute generateInterview() " );
            return ResponseEntity.ok().body(new GenericResponseDTO<>(
                    CommonController.SUCCESS, HttpStatus.OK.value(), null, null, null,interviewService.generaInterview(userId)));
        }catch(ResponseStatusException ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(
                    CommonController.ERROR, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), "service execute", null), HttpStatus.NOT_FOUND);
        }catch(Exception ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(
                    CommonController.ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(),ex.getMessage(), "service execute", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getInterviews/{user_id}") //obtiene los overviews de las entrevistas de un determinado usuario
    public ResponseEntity<GenericResponseDTO<InterviewRecordsDTO>> getInterviews(@PathVariable Long user_id){
        try{
            logger.info("Execute getInterviews() " );
            return ResponseEntity.ok().body(new GenericResponseDTO<>(
                    CommonController.SUCCESS, HttpStatus.OK.value(), null, null, null,interviewService.filtrarOverviews(user_id)));
        }catch(ResponseStatusException ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(
                    CommonController.ERROR, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), "service execute", null), HttpStatus.NOT_FOUND);
        }catch(Exception ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(
                    CommonController.ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage(), "service execute", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getInterview/{user_id}/{interview_id}") //obtiene una entrevista realizada por un determinado usuario
    public ResponseEntity<GenericResponseDTO<InterviewJoinResultsDTO>> getInterview(@PathVariable Long user_id, @PathVariable Long interview_id){
        try{
            logger.info("Execute getInterview() " );
            return ResponseEntity.ok().body(new GenericResponseDTO<>(
                    CommonController.SUCCESS, HttpStatus.OK.value(), null, null, null,interviewService.filtrarInterview(user_id,interview_id)));
        }catch(ResponseStatusException ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(
                    CommonController.ERROR, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), "service execute", null), HttpStatus.NOT_FOUND);
        }catch(Exception ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(
                    CommonController.ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage(), "service execute", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/setUserAnswers") //setea las respuestas del usuario sobre una determinada entrevista
    public ResponseEntity<GenericResponseDTO> setUserAnswers(@Valid @RequestBody QuestionJoinAnswerInputsDTO qJaOsDTOs){
        try{
            logger.info("Execute setUserAnswers() " );
            interviewService.setUserAnswers(qJaOsDTOs);
            return ResponseEntity.ok().body(new GenericResponseDTO<>(
                   CommonController.SUCCESS, HttpStatus.OK.value(), null, null, null,null));
        }catch(ResponseStatusException ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(
                    CommonController.ERROR, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), "service execute", null), HttpStatus.NOT_FOUND);
        }catch(Exception ex){
            logger.error("Exception: " + ex.getMessage());
            return new ResponseEntity<>(new GenericResponseDTO<>(
                    CommonController.ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage(), "service execute", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
