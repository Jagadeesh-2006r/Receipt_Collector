package com.campus.placement.placement_management_system.config;

import com.campus.placement.placement_management_system.model.Department;
import com.campus.placement.placement_management_system.model.Role;
import com.campus.placement.placement_management_system.model.User;
import com.campus.placement.placement_management_system.repository.UserRepository;
import com.campus.placement.placement_management_system.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Create sample departments
        if (departmentRepository.count() == 0) {
            Department cse = new Department();
            cse.setName("Computer Science Engineering");
            cse.setDescription("Computer Science and Engineering Department");
            departmentRepository.save(cse);
            
            Department it = new Department();
            it.setName("Information Technology");
            it.setDescription("Information Technology Department");
            departmentRepository.save(it);
        }
        
        // Create default placement officer if not exists
        if (!userRepository.existsByEmail("officer@college.edu")) {
            User officer = new User();
            officer.setEmail("officer@college.edu");
            officer.setPassword("password123");
            officer.setFullName("Placement Officer");
            officer.setRole(Role.PLACEMENT_OFFICER);
            officer.setPhone("9876543210");
            userRepository.save(officer);
        }
        
        // Create sample student
        if (!userRepository.existsByEmail("student@college.edu")) {
            User student = new User();
            student.setEmail("student@college.edu");
            student.setPassword("password123");
            student.setFullName("John Doe");
            student.setRole(Role.STUDENT);
            student.setPhone("9876543211");
            student.setRollNumber("CS2021001");
            student.setCgpa(8.5);
            userRepository.save(student);
        }
        
        // Create sample recruiter
        if (!userRepository.existsByEmail("recruiter@company.com")) {
            User recruiter = new User();
            recruiter.setEmail("recruiter@company.com");
            recruiter.setPassword("password123");
            recruiter.setFullName("Jane Smith");
            recruiter.setRole(Role.RECRUITER);
            recruiter.setPhone("9876543212");
            recruiter.setCompanyName("Tech Corp");
            recruiter.setDesignation("HR Manager");
            userRepository.save(recruiter);
        }
    }
}