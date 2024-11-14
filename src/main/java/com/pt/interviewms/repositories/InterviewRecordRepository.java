package com.pt.interviewms.repositories;

import com.pt.interviewms.models.entity.InterviewRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewRecordRepository extends CrudRepository<InterviewRecord, Long> {
    Optional<InterviewRecord> findByInterviewIdAndUserId(Long interviewId, Long userId);
    Optional<List<InterviewRecord>> findAllByUserId(Long userId);
}
