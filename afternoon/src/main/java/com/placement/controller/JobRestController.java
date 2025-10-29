package com.placement.controller;

import com.placement.entity.Job;
import com.placement.entity.User;
import com.placement.service.JobService;
import com.placement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jobs")
public class JobRestController {
    
    @Autowired
    private JobService jobService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping
    public ResponseEntity<?> createJob(@RequestBody Job job, Authentication authentication) {
        try {
            Optional<User> userOpt = userService.findByEmail(authentication.getName());
            if (userOpt.isEmpty() || userOpt.get().getRole() != User.Role.RECRUITER) {
                return ResponseEntity.badRequest().body("Only recruiters can create jobs");
            }
            
            job.setRecruiter(userOpt.get());
            Job savedJob = jobService.createJob(job);
            return ResponseEntity.ok(savedJob);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create job: " + e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getActiveJobs();
        return ResponseEntity.ok(jobs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id) {
        Optional<Job> jobOpt = jobService.getJobById(id);
        if (jobOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jobOpt.get());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(@PathVariable Long id, @RequestBody Job jobForm, Authentication authentication) {
        try {
            Optional<User> userOpt = userService.findByEmail(authentication.getName());
            Optional<Job> jobOpt = jobService.getJobById(id);
            
            if (userOpt.isEmpty() || jobOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Job existingJob = jobOpt.get();
            User user = userOpt.get();
            
            // Check if user is recruiter and owns this job
            if (user.getRole() != User.Role.RECRUITER || 
                !existingJob.getRecruiter().getUserId().equals(user.getUserId())) {
                return ResponseEntity.badRequest().body("Unauthorized to update this job");
            }
            
            // Update job details
            existingJob.setCompanyName(jobForm.getCompanyName());
            existingJob.setPosition(jobForm.getPosition());
            existingJob.setDescription(jobForm.getDescription());
            existingJob.setEligibilityCriteria(jobForm.getEligibilityCriteria());
            existingJob.setMinCgpa(jobForm.getMinCgpa());
            existingJob.setAllowedDepartments(jobForm.getAllowedDepartments());
            existingJob.setAllowedBatches(jobForm.getAllowedBatches());
            existingJob.setSalary(jobForm.getSalary());
            existingJob.setLocation(jobForm.getLocation());
            existingJob.setDeadline(jobForm.getDeadline());
            
            Job updatedJob = jobService.updateJob(existingJob);
            return ResponseEntity.ok(updatedJob);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update job: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id, Authentication authentication) {
        try {
            Optional<User> userOpt = userService.findByEmail(authentication.getName());
            Optional<Job> jobOpt = jobService.getJobById(id);
            
            if (userOpt.isEmpty() || jobOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Job job = jobOpt.get();
            User user = userOpt.get();
            
            // Check if user is recruiter and owns this job
            if (user.getRole() != User.Role.RECRUITER || 
                !job.getRecruiter().getUserId().equals(user.getUserId())) {
                return ResponseEntity.badRequest().body("Unauthorized to delete this job");
            }
            
            jobService.deleteJob(id);
            return ResponseEntity.ok("Job deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete job: " + e.getMessage());
        }
    }
}