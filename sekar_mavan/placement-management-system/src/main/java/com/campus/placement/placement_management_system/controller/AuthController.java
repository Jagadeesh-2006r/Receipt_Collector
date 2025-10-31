package com.campus.placement.placement_management_system.controller;

import com.campus.placement.placement_management_system.model.Role;
import com.campus.placement.placement_management_system.model.User;
import com.campus.placement.placement_management_system.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "login-simple";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, 
                       HttpSession session, Model model) {
        System.out.println("Login attempt for email: " + email);
        User user = authService.login(email, password);
        if (user != null) {
            System.out.println("Login successful for user: " + user.getFullName() + " with role: " + user.getRole());
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        }
        System.out.println("Login failed for email: " + email);
        model.addAttribute("error", "Invalid credentials");
        return "login-simple";
    }
    
    @GetMapping("/register")
    public String registerPage() {
        return "register-modern";
    }
    
    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        try {
            authService.register(user);
            model.addAttribute("success", "Registration successful! Please login.");
            return "register-modern";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        System.out.println("Dashboard access - User in session: " + (user != null ? user.getFullName() + " (" + user.getRole() + ")" : "null"));
        if (user == null) {
            System.out.println("No user in session, redirecting to login");
            return "redirect:/login";
        }
        
        model.addAttribute("user", user);
        if (user.getRole() == Role.STUDENT) {
            System.out.println("Redirecting to student dashboard");
            return "student-dashboard-ai";
        } else if (user.getRole() == Role.PLACEMENT_OFFICER) {
            System.out.println("Redirecting to officer dashboard");
            return "officer-dashboard-modern";
        } else if (user.getRole() == Role.RECRUITER) {
            System.out.println("Redirecting to recruiter dashboard");
            return "recruiter-dashboard";
        } else {
            System.out.println("Unknown role, redirecting to login");
            return "redirect:/login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
    @GetMapping("/applications-direct")
    public String applicationsDirect(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        return "applications-modern";
    }
}