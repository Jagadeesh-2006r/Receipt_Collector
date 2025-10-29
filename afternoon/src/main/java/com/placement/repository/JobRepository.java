package com.placement.repository;

import com.placement.entity.Job;
import com.placement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByRecruiter(User recruiter);
    List<Job> findByCompanyName(String companyName);
    List<Job> findByDeadlineAfter(LocalDate date);
    
    @Query("SELECT j FROM Job j WHERE j.deadline >= CURRENT_DATE ORDER BY j.createdAt DESC")
    List<Job> findActiveJobs();
    
    @Query("SELECT j FROM Job j WHERE j.minCgpa <= ?1 AND j.allowedDepartments LIKE %?2% AND j.deadline >= CURRENT_DATE")
    List<Job> findEligibleJobsForStudent(Double cgpa, String department);
    
    @Query("SELECT COUNT(j) FROM Job j WHERE j.recruiter.userId = ?1")
    Long countJobsByRecruiter(Long recruiterId);
}