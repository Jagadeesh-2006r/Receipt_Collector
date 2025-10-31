package com.campus.placement.placement_management_system.service;

import com.campus.placement.placement_management_system.model.Application;
import com.campus.placement.placement_management_system.model.Job;
import com.campus.placement.placement_management_system.model.User;
import com.campus.placement.placement_management_system.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
    public List<Application> getApplicationsByStudent(User student) {
        try {
            if (student == null) {
                System.out.println("Student is null in getApplicationsByStudent");
                return java.util.Collections.emptyList();
            }
            List<Application> applications = applicationRepository.findByStudent(student);
            System.out.println("Found " + applications.size() + " applications for student ID: " + student.getId());
            return applications;
        } catch (Exception e) {
            System.out.println("Error in getApplicationsByStudent: " + e.getMessage());
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }
    
    public List<Application> getApplicationsByJob(Job job) {
        return applicationRepository.findByJob(job);
    }
    
    public List<Application> getApplicationsByStatus(Application.ApplicationStatus status) {
        return applicationRepository.findByStatus(status);
    }
    
    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }
    
    public Application saveApplication(Application application) {
        return applicationRepository.save(application);
    }
    
    public boolean hasStudentApplied(Job job, User student) {
        return applicationRepository.existsByJobAndStudent(job, student);
    }
    
    public Application updateApplicationStatus(Long applicationId, Application.ApplicationStatus status) {
        Optional<Application> app = applicationRepository.findById(applicationId);
        if (app.isPresent()) {
            app.get().setStatus(status);
            return applicationRepository.save(app.get());
        }
        return null;
    }
}