package com.campus.placement.placement_management_system.controller;

import com.campus.placement.placement_management_system.model.Role;
import com.campus.placement.placement_management_system.model.User;
import com.campus.placement.placement_management_system.service.PlacementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reports")
public class ReportController {
    
    @Autowired
    private PlacementService placementService;
    
    @GetMapping
    public String viewReports(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.PLACEMENT_OFFICER) {
            return "redirect:/login";
        }
        
        model.addAttribute("totalPlacements", placementService.getTotalPlacements());
        model.addAttribute("companyStats", placementService.getPlacementStatsByCompany());
        model.addAttribute("placements", placementService.getAllAcceptedPlacements());
        model.addAttribute("user", user);
        
        return "reports";
    }
}