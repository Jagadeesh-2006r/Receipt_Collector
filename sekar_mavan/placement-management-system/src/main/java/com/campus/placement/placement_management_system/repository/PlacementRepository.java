package com.campus.placement.placement_management_system.repository;

import com.campus.placement.placement_management_system.model.Placement;
import com.campus.placement.placement_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlacementRepository extends JpaRepository<Placement, Long> {
    List<Placement> findByStudent(User student);
    List<Placement> findByAcceptedTrue();
    
    @Query("SELECT COUNT(p) FROM Placement p WHERE p.accepted = true")
    Long countAcceptedPlacements();
    
    @Query("SELECT p.job.companyName, COUNT(p) FROM Placement p WHERE p.accepted = true GROUP BY p.job.companyName")
    List<Object[]> getPlacementStatsByCompany();
}