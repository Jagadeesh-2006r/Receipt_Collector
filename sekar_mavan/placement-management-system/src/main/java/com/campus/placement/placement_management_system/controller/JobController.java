package com.campus.placement.placement_management_system.controller;

import com.campus.placement.placement_management_system.model.Job;
import com.campus.placement.placement_management_system.model.Role;
import com.campus.placement.placement_management_system.model.User;
import com.campus.placement.placement_management_system.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/jobs")
public class JobController {
    
    @Autowired
    private JobService jobService;
    
    @GetMapping
    public String listJobs(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        System.out.println("Jobs page access - User in session: " + (user != null ? user.getFullName() + " (" + user.getRole() + ")" : "null"));
        if (user == null) {
            System.out.println("No user in session for jobs page, redirecting to login");
            return "redirect:/login";
        }
        
        List<Job> jobs;
        if (user.getRole() == Role.STUDENT) {
            Double cgpa = user.getCgpa() != null ? user.getCgpa() : 0.0;
            jobs = jobService.getEligibleJobsForStudent(cgpa);
        } else if (user.getRole() == Role.RECRUITER) {
            jobs = jobService.getJobsByRecruiter(user);
        } else {
            jobs = jobService.getAllActiveJobs();
        }
        
        model.addAttribute("jobs", jobs);
        model.addAttribute("user", user);
        return "jobs";
    }
    
    @GetMapping("/create")
    public String createJobForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.RECRUITER) {
            return "redirect:/login";
        }
        model.addAttribute("job", new Job());
        return "create-job";
    }
    
    @PostMapping("/create")
    public String createJob(@ModelAttribute Job job, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.RECRUITER) {
            return "redirect:/login";
        }
        
        job.setRecruiter(user);
        jobService.saveJob(job);
        return "redirect:/jobs";
    }
    
    @GetMapping("/{id}")
    public String viewJob(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        Job job = jobService.getJobById(id).orElse(null);
        if (job == null) return "redirect:/jobs";
        
        model.addAttribute("job", job);
        model.addAttribute("user", user);
        return "job-details";
    }
}