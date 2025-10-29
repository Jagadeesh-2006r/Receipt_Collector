package com.placement.service;

import com.placement.entity.Placement;
import com.placement.entity.Job;
import com.placement.entity.User;
import com.placement.repository.PlacementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlacementService {
    
    @Autowired
    private PlacementRepository placementRepository;
    
    @Autowired
    private JobService jobService;
    
    public Placement finalizePlacement(Long studentId, Long jobId, Placement.PlacementStatus status, 
                                     Double finalSalary, String offerLetterLink) {
        Optional<Job> jobOpt = jobService.getJobById(jobId);
        if (jobOpt.isEmpty()) {
            throw new IllegalArgumentException("Job not found");
        }
        
        Job job = jobOpt.get();
        User student = new User();
        student.setUserId(studentId);
        
        Placement placement = new Placement(student, job, status);
        placement.setFinalSalary(finalSalary);
        placement.setOfferLetterLink(offerLetterLink);
        
        return placementRepository.save(placement);
    }
    
    public List<Placement> getPlacementsByStudent(User student) {
        return placementRepository.findByStudent(student);
    }
    
    public List<Placement> getAllPlacements() {
        return placementRepository.findAll();
    }
    
    public Long getTotalPlacementCount() {
        return placementRepository.countTotalPlacements();
    }
    
    public Map<String, Object> getPlacementStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        // Total placements
        stats.put("totalPlacements", getTotalPlacementCount());
        
        // Average salary
        Double avgSalary = placementRepository.getAverageSalary();
        stats.put("averageSalary", avgSalary != null ? avgSalary : 0.0);
        
        // Company-wise placements
        List<Object[]> companyStats = placementRepository.getPlacementsByCompany();
        Map<String, Long> companyPlacements = new HashMap<>();
        for (Object[] stat : companyStats) {
            companyPlacements.put((String) stat[0], (Long) stat[1]);
        }
        stats.put("companyWisePlacements", companyPlacements);
        
        // Department-wise placements
        List<Object[]> deptStats = placementRepository.getPlacementsByDepartment();
        Map<String, Long> deptPlacements = new HashMap<>();
        for (Object[] stat : deptStats) {
            deptPlacements.put((String) stat[0], (Long) stat[1]);
        }
        stats.put("departmentWisePlacements", deptPlacements);
        
        return stats;
    }
    
    public Optional<Placement> getPlacementById(Long id) {
        return placementRepository.findById(id);
    }
    
    public Placement updatePlacement(Placement placement) {
        return placementRepository.save(placement);
    }
    
    public List<Placement> getPlacementsByStatus(Placement.PlacementStatus status) {
        return placementRepository.findByFinalStatus(status);
    }
    
    public Double getAverageSalary() {
        Double avg = placementRepository.getAverageSalary();
        return avg != null ? avg : 0.0;
    }
}