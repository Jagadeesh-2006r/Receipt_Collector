package com.campus.placement.placement_management_system.controller;

import com.campus.placement.placement_management_system.model.Role;
import com.campus.placement.placement_management_system.model.User;
import com.campus.placement.placement_management_system.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/recruiter")
public class RecruiterController {
    
    @Autowired
    private JobService jobService;
    
    @GetMapping("/jobs")
    public String recruiterJobs(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.RECRUITER) {
            return "redirect:/login";
        }
        
        model.addAttribute("jobs", jobService.getJobsByRecruiter(user));
        model.addAttribute("user", user);
        return "jobs";
    }
}