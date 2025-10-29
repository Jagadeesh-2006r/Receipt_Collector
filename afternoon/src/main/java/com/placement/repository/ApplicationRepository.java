package com.placement.repository;

import com.placement.entity.Application;
import com.placement.entity.Job;
import com.placement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudent(User student);
    List<Application> findByJob(Job job);
    Optional<Application> findByStudentAndJob(User student, Job job);
    List<Application> findByStatus(Application.ApplicationStatus status);
    
    @Query("SELECT a FROM Application a WHERE a.job.jobId = ?1 AND a.status = ?2")
    List<Application> findByJobAndStatus(Long jobId, Application.ApplicationStatus status);
    
    @Query("SELECT COUNT(a) FROM Application a WHERE a.student.userId = ?1")
    Long countApplicationsByStudent(Long studentId);
    
    @Query("SELECT COUNT(a) FROM Application a WHERE a.job.jobId = ?1")
    Long countApplicationsByJob(Long jobId);
}