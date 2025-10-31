package com.campus.placement.placement_management_system.service;

import com.campus.placement.placement_management_system.model.Job;
import com.campus.placement.placement_management_system.model.User;
import com.campus.placement.placement_management_system.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    
    @Autowired
    private JobRepository jobRepository;
    
    public List<Job> getAllActiveJobs() {
        return jobRepository.findByActiveTrue();
    }
    
    public List<Job> getJobsByRecruiter(User recruiter) {
        return jobRepository.findByRecruiter(recruiter);
    }
    
    public List<Job> getAvailableJobs() {
        return jobRepository.findByActiveTrueAndApplicationDeadlineAfter(LocalDate.now());
    }
    
    public List<Job> getEligibleJobsForStudent(Double cgpa) {
        if (cgpa == null) cgpa = 0.0;
        return jobRepository.findEligibleJobsForStudent(LocalDate.now(), cgpa);
    }
    
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }
    
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }
    
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
}