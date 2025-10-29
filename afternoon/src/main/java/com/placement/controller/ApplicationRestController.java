package com.placement.controller;

import com.placement.entity.Application;
import com.placement.entity.User;
import com.placement.service.ApplicationService;
import com.placement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
public class ApplicationRestController {
    
    @Autowired
    private ApplicationService applicationService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/apply")
    public ResponseEntity<?> applyForJob(@RequestBody Map<String, Object> applicationData, Authentication authentication) {
        try {
            Optional<User> userOpt = userService.findByEmail(authentication.getName());
            if (userOpt.isEmpty() || userOpt.get().getRole() != User.Role.STUDENT) {
                return ResponseEntity.badRequest().body("Only students can apply for jobs");
            }
            
            User student = userOpt.get();
            Long jobId = Long.valueOf(applicationData.get("jobId").toString());
            String resumePath = student.getResumePath();
            
            Application application = applicationService.applyForJob(student, jobId, resumePath);
            return ResponseEntity.ok(application);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to apply: " + e.getMessage());
        }
    }
    
    @GetMapping("/student/{id}")
    public ResponseEntity<?> getStudentApplications(@PathVariable Long id, Authentication authentication) {
        try {
            Optional<User> userOpt = userService.findByEmail(authentication.getName());
            if (userOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("User not found");
            }
            
            User user = userOpt.get();
            
            // Students can only view their own applications
            if (user.getRole() == User.Role.STUDENT && !user.getUserId().equals(id)) {
                return ResponseEntity.badRequest().body("Unauthorized access");
            }
            
            User student = new User();
            student.setUserId(id);
            List<Application> applications = applicationService.getApplicationsByStudent(student);
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get applications: " + e.getMessage());
        }
    }
    
    @GetMapping("/job/{id}")
    public ResponseEntity<?> getJobApplications(@PathVariable Long id, Authentication authentication) {
        try {
            Optional<User> userOpt = userService.findByEmail(authentication.getName());
            if (userOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("User not found");
            }
            
            User user = userOpt.get();
            
            // Only recruiters and placement officers can view job applications
            if (user.getRole() == User.Role.STUDENT) {
                return ResponseEntity.badRequest().body("Unauthorized access");
            }
            
            List<Application> applications = applicationService.getApplicationsByJobAndStatus(id, null);
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get applications: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable Long id, 
                                                   @RequestBody Map<String, Object> statusData,
                                                   Authentication authentication) {
        try {
            Optional<User> userOpt = userService.findByEmail(authentication.getName());
            if (userOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("User not found");
            }
            
            User user = userOpt.get();
            
            // Only recruiters and placement officers can update application status
            if (user.getRole() == User.Role.STUDENT) {
                return ResponseEntity.badRequest().body("Unauthorized access");
            }
            
            Application.ApplicationStatus status = Application.ApplicationStatus.valueOf(statusData.get("status").toString());
            String feedback = statusData.get("feedback") != null ? statusData.get("feedback").toString() : null;
            
            Application updatedApplication = applicationService.updateApplicationStatus(id, status, feedback);
            return ResponseEntity.ok(updatedApplication);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update status: " + e.getMessage());
        }
    }
}