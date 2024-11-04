package com.pt.interviewms.repositories;

import com.pt.interviewms.models.entity.InterviewRecord;
import com.pt.interviewms.models.entity.Question;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    Optional<List<Question>> findAllByInterviewId(Long interviewId);

    Optional<Question> findByQuestionId(Long questionId);

    @Query(value="SELECT TOP 2 * FROM Question q WHERE q.interview_id IS NULL AND q.user_id = ?1 ORDER BY NEWID()", nativeQuery=true)
    Optional<List<Question>> selectNullQuestionsByUserId_query(Long userId);
}
