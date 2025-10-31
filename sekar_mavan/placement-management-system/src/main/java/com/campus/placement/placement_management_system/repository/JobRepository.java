package com.campus.placement.placement_management_system.repository;

import com.campus.placement.placement_management_system.model.Job;
import com.campus.placement.placement_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByActiveTrue();
    List<Job> findByRecruiter(User recruiter);
    List<Job> findByActiveTrueAndApplicationDeadlineAfter(LocalDate date);
    
    @Query("SELECT j FROM Job j WHERE j.active = true AND j.applicationDeadline > :currentDate AND (j.minCgpa IS NULL OR j.minCgpa <= :cgpa)")
    List<Job> findEligibleJobsForStudent(@Param("currentDate") LocalDate currentDate, @Param("cgpa") Double cgpa);
}