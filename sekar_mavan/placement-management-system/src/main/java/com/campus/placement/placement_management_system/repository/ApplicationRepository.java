package com.campus.placement.placement_management_system.repository;

import com.campus.placement.placement_management_system.model.Application;
import com.campus.placement.placement_management_system.model.Job;
import com.campus.placement.placement_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudent(User student);
    List<Application> findByJob(Job job);
    List<Application> findByStatus(Application.ApplicationStatus status);
    Optional<Application> findByJobAndStudent(Job job, User student);
    boolean existsByJobAndStudent(Job job, User student);
}