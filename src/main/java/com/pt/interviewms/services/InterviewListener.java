package com.pt.interviewms.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InterviewListener {
    private static Logger logger = LoggerFactory.getLogger(InterviewListener.class);

    @KafkaListener(topics ="cvFieldsPublishJSON", groupId ="interviewMS") //se pueden agregar varios topics
    public void listen(String message) {
        logger.info("Received Messasge in group foo: "+message);
    }
}
