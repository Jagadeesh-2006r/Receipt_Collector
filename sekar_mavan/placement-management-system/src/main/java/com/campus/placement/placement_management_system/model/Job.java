package com.campus.placement.placement_management_system.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String companyName;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(columnDefinition = "TEXT")
    private String requirements;
    
    private String location;
    private Double salary;
    private String jobType; // Full-time, Internship, etc.
    
    // Eligibility criteria
    private Double minCgpa;
    private String eligibleDepartments; // Comma separated
    private Integer passingYear;
    
    @Column(name = "application_deadline")
    private LocalDate applicationDeadline;
    
    @ManyToOne
    @JoinColumn(name = "recruiter_id", nullable = false)
    private User recruiter;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private boolean active = true;
}