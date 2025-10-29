package com.placement.repository;

import com.placement.entity.Interview;
import com.placement.entity.Job;
import com.placement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findByJob(Job job);
    List<Interview> findByStudent(User student);
    List<Interview> findByResult(Interview.InterviewResult result);
    
    @Query("SELECT i FROM Interview i WHERE i.interviewDate BETWEEN ?1 AND ?2")
    List<Interview> findInterviewsBetweenDates(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT i FROM Interview i WHERE i.job.jobId = ?1 AND i.roundNumber = ?2")
    List<Interview> findByJobAndRound(Long jobId, Integer roundNumber);
    
    @Query("SELECT i FROM Interview i WHERE i.student.userId = ?1 AND i.result = 'SCHEDULED'")
    List<Interview> findUpcomingInterviewsForStudent(Long studentId);
}