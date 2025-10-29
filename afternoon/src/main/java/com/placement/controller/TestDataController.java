package com.placement.controller;

import com.placement.entity.*;
import com.placement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/test")
public class TestDataController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JobService jobService;
    
    @Autowired
    private ApplicationService applicationService;
    
    @Autowired
    private InterviewService interviewService;
    
    @GetMapping("/create-sample-data")
    public String createSampleData(Authentication authentication) {
        try {
            Optional<User> userOpt = userService.findByEmail(authentication.getName());
            if (userOpt.isEmpty()) {
                return "redirect:/login";
            }
            
            User currentUser = userOpt.get();
            
            // Create sample job if user is recruiter
            if (currentUser.getRole() == User.Role.RECRUITER) {
                Job sampleJob = new Job();
                sampleJob.setCompanyName("Tech Corp");
                sampleJob.setPosition("Software Developer");
                sampleJob.setDescription("Exciting opportunity for software development");
                sampleJob.setLocation("Bangalore");
                sampleJob.setSalary(12.0);
                sampleJob.setMinCgpa(7.0);
                sampleJob.setAllowedDepartments("Computer Science,Information Technology");
                sampleJob.setAllowedBatches("2024,2025");
                sampleJob.setDeadline(LocalDate.now().plusDays(30));
                sampleJob.setRecruiter(currentUser);
                
                jobService.createJob(sampleJob);
            }
            
            // Create sample interview if user is student
            if (currentUser.getRole() == User.Role.STUDENT) {
                // Find any available job
                var jobs = jobService.getActiveJobs();
                if (!jobs.isEmpty()) {
                    Job job = jobs.get(0);
                    
                    // Create application first
                    try {
                        applicationService.applyForJob(currentUser, job.getJobId(), currentUser.getResumePath());
                        
                        // Create sample interview
                        Interview interview = new Interview();
                        interview.setJob(job);
                        interview.setStudent(currentUser);
                        interview.setRoundNumber(1);
                        interview.setRoundType("Technical Round");
                        interview.setInterviewDate(LocalDateTime.now().plusDays(3));
                        interview.setLocation("Conference Room A");
                        interview.setResult(Interview.InterviewResult.SCHEDULED);
                        
                        interviewService.saveInterview(interview);
                    } catch (Exception e) {
                        // Application might already exist, ignore
                    }
                }
            }
            
            return "redirect:/dashboard";
        } catch (Exception e) {
            return "redirect:/dashboard";
        }
    }
}