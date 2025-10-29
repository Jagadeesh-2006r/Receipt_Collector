package com.placement.repository;

import com.placement.entity.Placement;
import com.placement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlacementRepository extends JpaRepository<Placement, Long> {
    List<Placement> findByStudent(User student);
    List<Placement> findByFinalStatus(Placement.PlacementStatus status);
    
    @Query("SELECT COUNT(p) FROM Placement p WHERE p.finalStatus = 'SELECTED'")
    Long countTotalPlacements();
    
    @Query("SELECT p.job.companyName, COUNT(p) FROM Placement p WHERE p.finalStatus = 'SELECTED' GROUP BY p.job.companyName")
    List<Object[]> getPlacementsByCompany();
    
    @Query("SELECT p.student.department, COUNT(p) FROM Placement p WHERE p.finalStatus = 'SELECTED' GROUP BY p.student.department")
    List<Object[]> getPlacementsByDepartment();
    
    @Query("SELECT AVG(p.finalSalary) FROM Placement p WHERE p.finalStatus = 'SELECTED' AND p.finalSalary IS NOT NULL")
    Double getAverageSalary();
}