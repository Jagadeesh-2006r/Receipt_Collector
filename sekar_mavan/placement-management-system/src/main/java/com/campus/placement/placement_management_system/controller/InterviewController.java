package com.campus.placement.placement_management_system.controller;

import com.campus.placement.placement_management_system.model.Application;
import com.campus.placement.placement_management_system.model.Interview;
import com.campus.placement.placement_management_system.model.Role;
import com.campus.placement.placement_management_system.model.User;
import com.campus.placement.placement_management_system.service.ApplicationService;
import com.campus.placement.placement_management_system.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/interviews")
public class InterviewController {
    
    @Autowired
    private InterviewService interviewService;
    
    @Autowired
    private ApplicationService applicationService;
    
    @GetMapping
    public String listInterviews(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        model.addAttribute("interviews", interviewService.getInterviewsByStatus(Interview.InterviewStatus.SCHEDULED));
        model.addAttribute("user", user);
        return "interviews-simple";
    }
    
    @GetMapping("/schedule/{applicationId}")
    public String scheduleInterviewForm(@PathVariable Long applicationId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.PLACEMENT_OFFICER) {
            return "redirect:/login";
        }
        
        Application application = applicationService.getApplicationById(applicationId).orElse(null);
        if (application == null) return "redirect:/applications";
        
        model.addAttribute("application", application);
        model.addAttribute("interview", new Interview());
        return "schedule-interview-simple";
    }
    
    @PostMapping("/schedule")
    public String scheduleInterview(@ModelAttribute Interview interview, 
                                  @RequestParam Long applicationId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.PLACEMENT_OFFICER) {
            return "redirect:/login";
        }
        
        Application application = applicationService.getApplicationById(applicationId).orElse(null);
        if (application == null) return "redirect:/applications";
        
        interview.setApplication(application);
        interviewService.saveInterview(interview);
        
        return "redirect:/interviews";
    }
    
    @PostMapping("/{id}/status")
    public String updateInterviewStatus(@PathVariable Long id, 
                                      @RequestParam Interview.InterviewStatus status,
                                      HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.PLACEMENT_OFFICER) {
            return "redirect:/login";
        }
        
        interviewService.updateInterviewStatus(id, status);
        return "redirect:/interviews";
    }
}