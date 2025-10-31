package com.campus.placement.placement_management_system.service;

import com.campus.placement.placement_management_system.model.Placement;
import com.campus.placement.placement_management_system.model.User;
import com.campus.placement.placement_management_system.repository.PlacementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PlacementService {
    
    @Autowired
    private PlacementRepository placementRepository;
    
    public List<Placement> getPlacementsByStudent(User student) {
        return placementRepository.findByStudent(student);
    }
    
    public List<Placement> getAllAcceptedPlacements() {
        return placementRepository.findByAcceptedTrue();
    }
    
    public Optional<Placement> getPlacementById(Long id) {
        return placementRepository.findById(id);
    }
    
    public Placement savePlacement(Placement placement) {
        return placementRepository.save(placement);
    }
    
    public Long getTotalPlacements() {
        return placementRepository.countAcceptedPlacements();
    }
    
    public List<Object[]> getPlacementStatsByCompany() {
        return placementRepository.getPlacementStatsByCompany();
    }
}