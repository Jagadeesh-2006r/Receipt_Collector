package com.campus.placement.placement_management_system.controller;

import com.campus.placement.placement_management_system.model.Application;
import com.campus.placement.placement_management_system.model.Job;
import com.campus.placement.placement_management_system.model.Role;
import com.campus.placement.placement_management_system.model.User;
import com.campus.placement.placement_management_system.service.ApplicationService;
import com.campus.placement.placement_management_system.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/applications")
public class ApplicationController {
    
    @Autowired
    private ApplicationService applicationService;
    
    @Autowired
    private JobService jobService;
    
    @GetMapping
    public String listApplications(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        try {
            List<Application> applications = applicationService.getApplicationsByStudent(user);
            model.addAttribute("applications", applications);
        } catch (Exception e) {
            model.addAttribute("applications", java.util.Collections.emptyList());
        }
        
        model.addAttribute("user", user);
        return "applications-modern";
    }
    
    @PostMapping("/apply/{jobId}")
    public String applyForJob(@PathVariable Long jobId, 
                             @RequestParam(required = false, defaultValue = "") String coverLetter, 
                             HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.STUDENT) {
            return "redirect:/login";
        }
        
        Job job = jobService.getJobById(jobId).orElse(null);
        if (job == null) {
            return "redirect:/jobs";
        }
        
        if (applicationService.hasStudentApplied(job, user)) {
            return "redirect:/jobs/" + jobId + "?error=already_applied";
        }
        
        try {
            Application application = new Application();
            application.setJob(job);
            application.setStudent(user);
            application.setCoverLetter(coverLetter);
            
            applicationService.saveApplication(application);
            return "redirect:/applications?success=applied";
        } catch (Exception e) {
            return "redirect:/jobs/" + jobId + "?error=application_failed";
        }
    }
    
    @PostMapping("/{id}/status")
    public String updateApplicationStatus(@PathVariable Long id, 
                                        @RequestParam Application.ApplicationStatus status,
                                        HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.PLACEMENT_OFFICER) {
            return "redirect:/login";
        }
        
        applicationService.updateApplicationStatus(id, status);
        return "redirect:/applications";
    }
}