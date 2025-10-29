package com.placement.service;

import com.placement.entity.Job;
import com.placement.entity.User;
import com.placement.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    
    @Autowired
    private JobRepository jobRepository;
    
    public Job createJob(Job job) {
        return jobRepository.save(job);
    }
    
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
    
    public List<Job> getActiveJobs() {
        return jobRepository.findActiveJobs();
    }
    
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }
    
    public List<Job> getJobsByRecruiter(User recruiter) {
        return jobRepository.findByRecruiter(recruiter);
    }
    
    public Job updateJob(Job job) {
        return jobRepository.save(job);
    }
    
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
    
    public List<Job> getEligibleJobsForStudent(User student) {
        if (student.getCgpa() == null || student.getDepartment() == null) {
            return List.of();
        }
        return jobRepository.findEligibleJobsForStudent(student.getCgpa(), student.getDepartment());
    }
    
    public boolean isStudentEligible(User student, Job job) {
        if (student.getCgpa() == null || student.getDepartment() == null) {
            return false;
        }
        
        // Check CGPA requirement
        if (job.getMinCgpa() != null && student.getCgpa() < job.getMinCgpa()) {
            return false;
        }
        
        // Check department requirement
        if (job.getAllowedDepartments() != null && 
            !job.getAllowedDepartments().contains(student.getDepartment())) {
            return false;
        }
        
        // Check batch requirement
        if (job.getAllowedBatches() != null && 
            !job.getAllowedBatches().contains(student.getBatch())) {
            return false;
        }
        
        // Check deadline
        if (job.getDeadline() != null && job.getDeadline().isBefore(LocalDate.now())) {
            return false;
        }
        
        return true;
    }
    
    public List<Job> searchJobs(String companyName) {
        if (companyName != null && !companyName.isEmpty()) {
            return jobRepository.findByCompanyName(companyName);
        }
        return getAllJobs();
    }
}