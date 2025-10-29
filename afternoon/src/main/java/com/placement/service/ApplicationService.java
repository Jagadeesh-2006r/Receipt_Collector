package com.placement.service;

import com.placement.entity.Application;
import com.placement.entity.Job;
import com.placement.entity.User;
import com.placement.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Autowired
    private JobService jobService;
    
    public Application applyForJob(User student, Long jobId, String resumePath) {
        Optional<Job> jobOpt = jobService.getJobById(jobId);
        if (jobOpt.isEmpty()) {
            throw new IllegalArgumentException("Job not found");
        }
        
        Job job = jobOpt.get();
        
        // Check if student is eligible
        if (!jobService.isStudentEligible(student, job)) {
            throw new IllegalArgumentException("Student is not eligible for this job");
        }
        
        // Check if already applied
        if (applicationRepository.findByStudentAndJob(student, job).isPresent()) {
            throw new IllegalArgumentException("Already applied for this job");
        }
        
        Application application = new Application(student, job, resumePath);
        return applicationRepository.save(application);
    }
    
    public List<Application> getApplicationsByStudent(User student) {
        return applicationRepository.findByStudent(student);
    }
    
    public List<Application> getApplicationsByJob(Job job) {
        return applicationRepository.findByJob(job);
    }
    
    public List<Application> getApplicationsByJobAndStatus(Long jobId, Application.ApplicationStatus status) {
        if (status == null) {
            return applicationRepository.findAll().stream()
                    .filter(app -> app.getJob().getJobId().equals(jobId))
                    .toList();
        }
        return applicationRepository.findByJobAndStatus(jobId, status);
    }
    
    public Application updateApplicationStatus(Long applicationId, Application.ApplicationStatus status, String feedback) {
        Optional<Application> appOpt = applicationRepository.findById(applicationId);
        if (appOpt.isEmpty()) {
            throw new IllegalArgumentException("Application not found");
        }
        
        Application application = appOpt.get();
        application.setStatus(status);
        if (feedback != null) {
            application.setFeedback(feedback);
        }
        
        return applicationRepository.save(application);
    }
    
    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }
    
    public List<Application> getShortlistedApplications() {
        return applicationRepository.findByStatus(Application.ApplicationStatus.SHORTLISTED);
    }
    
    public Long getApplicationCountByStudent(Long studentId) {
        return applicationRepository.countApplicationsByStudent(studentId);
    }
    
    public Long getApplicationCountByJob(Long jobId) {
        return applicationRepository.countApplicationsByJob(jobId);
    }
    
    public boolean hasStudentApplied(User student, Job job) {
        return applicationRepository.findByStudentAndJob(student, job).isPresent();
    }
}