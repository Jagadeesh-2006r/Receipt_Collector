package com.placement.controller;

import com.placement.entity.Application;
import com.placement.entity.Job;
import com.placement.entity.User;
import com.placement.service.ApplicationService;
import com.placement.service.JobService;
import com.placement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter")
public class RecruiterController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JobService jobService;
    
    @Autowired
    private ApplicationService applicationService;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        Optional<User> userOpt = userService.findByEmail(authentication.getName());
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }
        
        User recruiter = userOpt.get();
        List<Job> jobs = jobService.getJobsByRecruiter(recruiter);
        
        model.addAttribute("recruiter", recruiter);
        model.addAttribute("jobs", jobs);
        
        return "recruiter/dashboard";
    }
    
    @GetMapping("/jobs/create")
    public String createJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "recruiter/create-job";
    }
    
    @PostMapping("/jobs/create")
    public String createJob(@ModelAttribute Job job, Authentication authentication, Model model) {
        try {
            Optional<User> userOpt = userService.findByEmail(authentication.getName());
            if (userOpt.isEmpty()) {
                return "redirect:/login";
            }
            
            User recruiter = userOpt.get();
            job.setRecruiter(recruiter);
            jobService.createJob(job);
            
            model.addAttribute("success", "Job created successfully!");
            return "redirect:/recruiter/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create job: " + e.getMessage());
            model.addAttribute("job", job);
            return "recruiter/create-job";
        }
    }
    
    @GetMapping("/jobs/{id}")
    public String viewJob(@PathVariable Long id, Model model, Authentication authentication) {
        Optional<User> userOpt = userService.findByEmail(authentication.getName());
        Optional<Job> jobOpt = jobService.getJobById(id);
        
        if (userOpt.isEmpty() || jobOpt.isEmpty()) {
            return "redirect:/recruiter/dashboard";
        }
        
        Job job = jobOpt.get();
        List<Application> applications = applicationService.getApplicationsByJob(job);
        Long applicationCount = applicationService.getApplicationCountByJob(id);
        
        model.addAttribute("job", job);
        model.addAttribute("applications", applications);
        model.addAttribute("applicationCount", applicationCount);
        
        return "recruiter/job-details";
    }
    
    @GetMapping("/jobs/{id}/edit")
    public String editJobForm(@PathVariable Long id, Model model, Authentication authentication) {
        Optional<User> userOpt = userService.findByEmail(authentication.getName());
        Optional<Job> jobOpt = jobService.getJobById(id);
        
        if (userOpt.isEmpty() || jobOpt.isEmpty()) {
            return "redirect:/recruiter/dashboard";
        }
        
        Job job = jobOpt.get();
        User recruiter = userOpt.get();
        
        // Check if the recruiter owns this job
        if (!job.getRecruiter().getUserId().equals(recruiter.getUserId())) {
            return "redirect:/recruiter/dashboard";
        }
        
        model.addAttribute("job", job);
        return "recruiter/edit-job";
    }
    
    @PostMapping("/jobs/{id}/edit")
    public String editJob(@PathVariable Long id, @ModelAttribute Job jobForm, 
                         Authentication authentication, Model model) {
        try {
            Optional<User> userOpt = userService.findByEmail(authentication.getName());
            Optional<Job> jobOpt = jobService.getJobById(id);
            
            if (userOpt.isEmpty() || jobOpt.isEmpty()) {
                return "redirect:/recruiter/dashboard";
            }
            
            Job existingJob = jobOpt.get();
            User recruiter = userOpt.get();
            
            // Check if the recruiter owns this job
            if (!existingJob.getRecruiter().getUserId().equals(recruiter.getUserId())) {
                return "redirect:/recruiter/dashboard";
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
            
            jobService.updateJob(existingJob);
            
            model.addAttribute("success", "Job updated successfully!");
            return "redirect:/recruiter/jobs/" + id;
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update job: " + e.getMessage());
            model.addAttribute("job", jobForm);
            return "recruiter/edit-job";
        }
    }
    
    @PostMapping("/jobs/{id}/delete")
    public String deleteJob(@PathVariable Long id, Authentication authentication) {
        try {
            Optional<User> userOpt = userService.findByEmail(authentication.getName());
            Optional<Job> jobOpt = jobService.getJobById(id);
            
            if (userOpt.isEmpty() || jobOpt.isEmpty()) {
                return "redirect:/recruiter/dashboard";
            }
            
            Job job = jobOpt.get();
            User recruiter = userOpt.get();
            
            // Check if the recruiter owns this job
            if (!job.getRecruiter().getUserId().equals(recruiter.getUserId())) {
                return "redirect:/recruiter/dashboard";
            }
            
            jobService.deleteJob(id);
        } catch (Exception e) {
            // Handle error silently for now
        }
        
        return "redirect:/recruiter/dashboard";
    }
    
    @GetMapping("/applications/{id}/shortlist")
    public String shortlistApplication(@PathVariable Long id, Authentication authentication) {
        try {
            applicationService.updateApplicationStatus(id, Application.ApplicationStatus.SHORTLISTED, "Shortlisted by recruiter");
        } catch (Exception e) {
            // Handle error silently
        }
        
        return "redirect:/recruiter/dashboard";
    }
    
    @GetMapping("/applications/{id}/reject")
    public String rejectApplication(@PathVariable Long id, Authentication authentication) {
        try {
            applicationService.updateApplicationStatus(id, Application.ApplicationStatus.REJECTED, "Rejected by recruiter");
        } catch (Exception e) {
            // Handle error silently
        }
        
        return "redirect:/recruiter/dashboard";
    }
    
    @GetMapping("/applications")
    public String viewAllApplications(Model model, Authentication authentication) {
        Optional<User> userOpt = userService.findByEmail(authentication.getName());
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }
        
        User recruiter = userOpt.get();
        List<Job> jobs = jobService.getJobsByRecruiter(recruiter);
        
        model.addAttribute("jobs", jobs);
        model.addAttribute("recruiter", recruiter);
        
        return "recruiter/applications";
    }
}