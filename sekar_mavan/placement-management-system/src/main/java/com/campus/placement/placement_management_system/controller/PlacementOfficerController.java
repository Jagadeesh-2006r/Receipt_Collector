package com.campus.placement.placement_management_system.controller;

import com.campus.placement.placement_management_system.model.Role;
import com.campus.placement.placement_management_system.model.User;
import com.campus.placement.placement_management_system.service.ApplicationService;
import com.campus.placement.placement_management_system.service.InterviewService;
import com.campus.placement.placement_management_system.service.JobService;
import com.campus.placement.placement_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/officer")
public class PlacementOfficerController {
    
    @Autowired
    private JobService jobService;
    
    @Autowired
    private ApplicationService applicationService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private InterviewService interviewService;
    
    @GetMapping("/dashboard")
    public String officerDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.PLACEMENT_OFFICER) {
            return "redirect:/login";
        }
        
        try {
            // Get dynamic counts
            int totalJobs = jobService.getAllActiveJobs().size();
            int totalStudents = userService.getActiveStudents().size();
            int pendingApplications = applicationService.getApplicationsByStatus(com.campus.placement.placement_management_system.model.Application.ApplicationStatus.APPLIED).size();
            int scheduledInterviews = interviewService.getInterviewsByStatus(com.campus.placement.placement_management_system.model.Interview.InterviewStatus.SCHEDULED).size();
            
            model.addAttribute("totalJobs", totalJobs);
            model.addAttribute("totalStudents", totalStudents);
            model.addAttribute("pendingApplications", pendingApplications);
            model.addAttribute("scheduledInterviews", scheduledInterviews);
        } catch (Exception e) {
            // Fallback to default values if there's an error
            model.addAttribute("totalJobs", 0);
            model.addAttribute("totalStudents", 0);
            model.addAttribute("pendingApplications", 0);
            model.addAttribute("scheduledInterviews", 0);
        }
        
        model.addAttribute("user", user);
        return "officer-dashboard-modern";
    }
}