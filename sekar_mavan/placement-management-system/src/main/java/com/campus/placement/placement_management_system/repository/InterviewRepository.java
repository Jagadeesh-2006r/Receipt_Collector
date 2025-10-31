package com.campus.placement.placement_management_system.repository;

import com.campus.placement.placement_management_system.model.Application;
import com.campus.placement.placement_management_system.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findByApplication(Application application);
    List<Interview> findByStatus(Interview.InterviewStatus status);
}