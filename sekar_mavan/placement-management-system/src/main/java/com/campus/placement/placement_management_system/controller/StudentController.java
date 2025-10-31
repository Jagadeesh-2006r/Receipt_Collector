package com.campus.placement.placement_management_system.controller;

import com.campus.placement.placement_management_system.model.Role;
import com.campus.placement.placement_management_system.model.User;
import com.campus.placement.placement_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/students")
public class StudentController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String listStudents(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.PLACEMENT_OFFICER) {
            return "redirect:/login";
        }
        
        model.addAttribute("students", userService.getActiveStudents());
        model.addAttribute("user", user);
        return "students";
    }
    
    @GetMapping("/profile")
    public String viewProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.STUDENT) {
            return "redirect:/login";
        }
        
        model.addAttribute("user", user);
        return "student-profile";
    }
    
    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute User updatedUser, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.STUDENT) {
            return "redirect:/login";
        }
        
        user.setFullName(updatedUser.getFullName());
        user.setPhone(updatedUser.getPhone());
        user.setCgpa(updatedUser.getCgpa());
        user.setResumePath(updatedUser.getResumePath());
        
        userService.saveUser(user);
        session.setAttribute("user", user);
        
        return "redirect:/students/profile";
    }
}