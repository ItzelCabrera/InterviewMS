package com.pt.interviewms.repositories;

import com.pt.interviewms.models.entity.Answer;
import com.pt.interviewms.models.entity.InterviewRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    Optional<Answer> findByAnswerId(Long answerId);
}
