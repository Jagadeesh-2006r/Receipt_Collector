package com.placement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;
    
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;
    
    private String resumePath;
    
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.APPLIED;
    
    @Column(name = "applied_at")
    private LocalDateTime appliedAt = LocalDateTime.now();
    
    private String feedback;
    
    public enum ApplicationStatus {
        APPLIED, SHORTLISTED, REJECTED, SELECTED
    }
    
    // Constructors
    public Application() {}
    
    public Application(User student, Job job, String resumePath) {
        this.student = student;
        this.job = job;
        this.resumePath = resumePath;
    }
    
    // Getters and Setters
    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
    
    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }
    
    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }
    
    public String getResumePath() { return resumePath; }
    public void setResumePath(String resumePath) { this.resumePath = resumePath; }
    
    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
    
    public LocalDateTime getAppliedAt() { return appliedAt; }
    public void setAppliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; }
    
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}