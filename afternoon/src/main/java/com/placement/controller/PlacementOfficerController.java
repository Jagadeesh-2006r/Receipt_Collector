package com.placement.controller;

import com.placement.entity.*;
import com.placement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/officer")
public class PlacementOfficerController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JobService jobService;
    
    @Autowired
    private ApplicationService applicationService;
    
    @Autowired
    private InterviewService interviewService;
    
    @Autowired
    private PlacementService placementService;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        Optional<User> userOpt = userService.findByEmail(authentication.getName());
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }
        
        User officer = userOpt.get();
        List<Job> activeJobs = jobService.getActiveJobs();
        List<Application> shortlistedApplications = applicationService.getShortlistedApplications();
        List<Interview> scheduledInterviews = interviewService.getScheduledInterviews();
        Map<String, Object> placementStats = placementService.getPlacementStatistics();
        
        model.addAttribute("officer", officer);
        model.addAttribute("activeJobs", activeJobs);
        model.addAttribute("shortlistedApplications", shortlistedApplications);
        model.addAttribute("scheduledInterviews", scheduledInterviews);
        model.addAttribute("placementStats", placementStats);
        
        return "officer/dashboard";
    }
    
    @GetMapping("/applications")
    public String viewApplications(Model model) {
        List<Application> applications = applicationService.getShortlistedApplications();
        model.addAttribute("applications", applications);
        return "officer/applications";
    }
    
    @PostMapping("/applications/{id}/update-status")
    public String updateApplicationStatus(@PathVariable Long id, 
                                        @RequestParam Application.ApplicationStatus status,
                                        @RequestParam(required = false) String feedback,
                                        Model model) {
        try {
            applicationService.updateApplicationStatus(id, status, feedback);
            model.addAttribute("success", "Application status updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update status: " + e.getMessage());
        }
        
        return "redirect:/officer/applications";
    }
    
    @GetMapping("/interviews")
    public String viewInterviews(Model model) {
        List<Interview> interviews = interviewService.getScheduledInterviews();
        model.addAttribute("interviews", interviews);
        return "officer/interviews";
    }
    
    @GetMapping("/interviews/schedule")
    public String scheduleInterviewForm(Model model) {
        List<Job> activeJobs = jobService.getActiveJobs();
        model.addAttribute("jobs", activeJobs);
        model.addAttribute("interview", new Interview());
        return "officer/schedule-interview";
    }
    
    @PostMapping("/interviews/schedule")
    public String scheduleInterview(@RequestParam Long jobId,
                                  @RequestParam Long studentId,
                                  @RequestParam Integer roundNumber,
                                  @RequestParam String roundType,
                                  @RequestParam String interviewDateTime,
                                  @RequestParam(required = false) String location,
                                  @RequestParam(required = false) String meetingLink,
                                  Model model) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(interviewDateTime);
            Interview interview = interviewService.scheduleInterview(jobId, studentId, roundNumber, dateTime, roundType, location);
            if (meetingLink != null && !meetingLink.isEmpty()) {
                interview.setMeetingLink(meetingLink);
                interviewService.updateInterview(interview);
            }
            
            model.addAttribute("success", "Interview scheduled successfully!");
            return "redirect:/officer/interviews";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to schedule interview: " + e.getMessage());
            List<Job> activeJobs = jobService.getActiveJobs();
            model.addAttribute("jobs", activeJobs);
            return "officer/schedule-interview";
        }
    }
    
    @GetMapping("/interviews/{id}/update")
    public String updateInterviewForm(@PathVariable Long id, Model model) {
        Optional<Interview> interviewOpt = interviewService.getInterviewById(id);
        if (interviewOpt.isEmpty()) {
            return "redirect:/officer/interviews";
        }
        
        model.addAttribute("interview", interviewOpt.get());
        return "officer/update-interview";
    }
    
    @PostMapping("/interviews/{id}/update")
    public String updateInterview(@PathVariable Long id,
                                @RequestParam Interview.InterviewResult result,
                                @RequestParam(required = false) String feedback,
                                @RequestParam(required = false) Integer score,
                                Model model) {
        try {
            interviewService.updateInterviewResult(id, result, feedback, score);
            model.addAttribute("success", "Interview updated successfully!");
            return "redirect:/officer/interviews";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update interview: " + e.getMessage());
            Optional<Interview> interviewOpt = interviewService.getInterviewById(id);
            if (interviewOpt.isPresent()) {
                model.addAttribute("interview", interviewOpt.get());
            }
            return "officer/update-interview";
        }
    }
    
    @GetMapping("/placements")
    public String viewPlacements(Model model) {
        List<Placement> placements = placementService.getAllPlacements();
        model.addAttribute("placements", placements);
        return "officer/placements";
    }
    
    @GetMapping("/placements/finalize")
    public String finalizePlacementForm(Model model) {
        List<User> students = userService.getAllStudents();
        List<Job> jobs = jobService.getAllJobs();
        
        model.addAttribute("students", students);
        model.addAttribute("jobs", jobs);
        model.addAttribute("placement", new Placement());
        
        return "officer/finalize-placement";
    }
    
    @PostMapping("/placements/finalize")
    public String finalizePlacement(@RequestParam Long studentId,
                                  @RequestParam Long jobId,
                                  @RequestParam Placement.PlacementStatus status,
                                  @RequestParam(required = false) Double finalSalary,
                                  @RequestParam(required = false) String offerLetterLink,
                                  Model model) {
        try {
            placementService.finalizePlacement(studentId, jobId, status, finalSalary, offerLetterLink);
            model.addAttribute("success", "Placement finalized successfully!");
            return "redirect:/officer/placements";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to finalize placement: " + e.getMessage());
            List<User> students = userService.getAllStudents();
            List<Job> jobs = jobService.getAllJobs();
            model.addAttribute("students", students);
            model.addAttribute("jobs", jobs);
            return "officer/finalize-placement";
        }
    }
    
    @GetMapping("/reports")
    public String viewReports(Model model) {
        Map<String, Object> placementStats = placementService.getPlacementStatistics();
        model.addAttribute("stats", placementStats);
        return "officer/reports";
    }
    
    @GetMapping("/students")
    public String viewStudents(Model model) {
        List<User> students = userService.getAllStudents();
        model.addAttribute("students", students);
        return "officer/students";
    }
    
    @GetMapping("/jobs")
    public String viewJobs(Model model) {
        List<Job> jobs = jobService.getAllJobs();
        model.addAttribute("jobs", jobs);
        return "officer/jobs";
    }
}