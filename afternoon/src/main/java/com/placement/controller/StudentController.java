package com.placement.controller;

import com.placement.entity.Application;
import com.placement.entity.Interview;
import com.placement.entity.Job;
import com.placement.entity.User;
import com.placement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JobService jobService;
    
    @Autowired
    private ApplicationService applicationService;
    
    @Autowired
    private InterviewService interviewService;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        Optional<User> userOpt = userService.findByEmail(authentication.getName());
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }
        
        User student = userOpt.get();
        List<Job> eligibleJobs = jobService.getEligibleJobsForStudent(student);
        List<Application> applications = applicationService.getApplicationsByStudent(student);
        List<Interview> upcomingInterviews = interviewService.getUpcomingInterviewsForStudent(student);
        
        model.addAttribute("student", student);
        model.addAttribute("eligibleJobs", eligibleJobs);
        model.addAttribute("applications", applications);
        model.addAttribute("upcomingInterviews", upcomingInterviews);
        
        return "student/dashboard";
    }
    
    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        Optional<User> userOpt = userService.findByEmail(authentication.getName());
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }
        
        model.addAttribute("student", userOpt.get());
        return "student/profile";
    }
    
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User user, Authentication authentication, Model model) {
        try {
            Optional<User> userOpt = userService.findByEmail(authentication.getName());
            if (userOpt.isEmpty()) {
                return "redirect:/login";
            }
            
            User existingUser = userOpt.get();
            existingUser.setName(user.getName());
            existingUser.setPhone(user.getPhone());
            existingUser.setDepartment(user.getDepartment());
            existingUser.setCgpa(user.getCgpa());
            existingUser.setBatch(user.getBatch());
            
            userService.updateUser(existingUser);
            model.addAttribute("success", "Profile updated successfully!");
            model.addAttribute("student", existingUser);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update profile: " + e.getMessage());
        }
        
        return "student/profile";
    }
    
    @PostMapping("/resume/upload")
    public String uploadResume(@RequestParam("resume") MultipartFile file, 
                              Authentication authentication, Model model) {
        try {
            Optional<User> userOpt = userService.findByEmail(authentication.getName());
            if (userOpt.isEmpty()) {
                return "redirect:/login";
            }
            
            User student = userOpt.get();
            String resumePath = userService.uploadResume(file, student.getUserId());
            student.setResumePath(resumePath);
            userService.updateUser(student);
            
            model.addAttribute("success", "Resume uploaded successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to upload resume: " + e.getMessage());
        }
        
        return "redirect:/student/profile";
    }
    
    @GetMapping("/jobs")
    public String viewJobs(Model model, Authentication authentication) {
        Optional<User> userOpt = userService.findByEmail(authentication.getName());
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }
        
        User student = userOpt.get();
        List<Job> eligibleJobs = jobService.getEligibleJobsForStudent(student);
        
        model.addAttribute("jobs", eligibleJobs);
        model.addAttribute("student", student);
        
        return "student/jobs";
    }
    
    @GetMapping("/jobs/{id}")
    public String viewJobDetails(@PathVariable Long id, Model model, Authentication authentication) {
        Optional<User> userOpt = userService.findByEmail(authentication.getName());
        Optional<Job> jobOpt = jobService.getJobById(id);
        
        if (userOpt.isEmpty() || jobOpt.isEmpty()) {
            return "redirect:/student/jobs";
        }
        
        User student = userOpt.get();
        Job job = jobOpt.get();
        boolean hasApplied = applicationService.hasStudentApplied(student, job);
        boolean isEligible = jobService.isStudentEligible(student, job);
        
        model.addAttribute("job", job);
        model.addAttribute("student", student);
        model.addAttribute("hasApplied", hasApplied);
        model.addAttribute("isEligible", isEligible);
        
        return "student/job-details";
    }
    
    @PostMapping("/jobs/{id}/apply")
    public String applyForJob(@PathVariable Long id, Authentication authentication, Model model) {
        try {
            Optional<User> userOpt = userService.findByEmail(authentication.getName());
            if (userOpt.isEmpty()) {
                return "redirect:/login";
            }
            
            User student = userOpt.get();
            applicationService.applyForJob(student, id, student.getResumePath());
            
            model.addAttribute("success", "Application submitted successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to apply: " + e.getMessage());
        }
        
        return "redirect:/student/jobs/" + id;
    }
    
    @GetMapping("/applications")
    public String viewApplications(Model model, Authentication authentication) {
        Optional<User> userOpt = userService.findByEmail(authentication.getName());
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }
        
        User student = userOpt.get();
        List<Application> applications = applicationService.getApplicationsByStudent(student);
        
        model.addAttribute("applications", applications);
        model.addAttribute("student", student);
        
        return "student/applications";
    }
    
    @GetMapping("/interviews")
    public String viewInterviews(Model model, Authentication authentication) {
        Optional<User> userOpt = userService.findByEmail(authentication.getName());
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }
        
        User student = userOpt.get();
        List<Interview> interviews = interviewService.getInterviewsByStudent(student);
        
        model.addAttribute("interviews", interviews);
        model.addAttribute("student", student);
        
        return "student/interviews";
    }
}