package com.placement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "placements")
public class Placement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placementId;
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;
    
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;
    
    @Enumerated(EnumType.STRING)
    private PlacementStatus finalStatus;
    
    private String offerLetterLink;
    private Double finalSalary;
    private LocalDateTime joiningDate;
    
    @Column(name = "placed_at")
    private LocalDateTime placedAt = LocalDateTime.now();
    
    public enum PlacementStatus {
        SELECTED, OFFER_ACCEPTED, OFFER_REJECTED, JOINED
    }
    
    // Constructors
    public Placement() {}
    
    public Placement(User student, Job job, PlacementStatus finalStatus) {
        this.student = student;
        this.job = job;
        this.finalStatus = finalStatus;
    }
    
    // Getters and Setters
    public Long getPlacementId() { return placementId; }
    public void setPlacementId(Long placementId) { this.placementId = placementId; }
    
    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }
    
    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }
    
    public PlacementStatus getFinalStatus() { return finalStatus; }
    public void setFinalStatus(PlacementStatus finalStatus) { this.finalStatus = finalStatus; }
    
    public String getOfferLetterLink() { return offerLetterLink; }
    public void setOfferLetterLink(String offerLetterLink) { this.offerLetterLink = offerLetterLink; }
    
    public Double getFinalSalary() { return finalSalary; }
    public void setFinalSalary(Double finalSalary) { this.finalSalary = finalSalary; }
    
    public LocalDateTime getJoiningDate() { return joiningDate; }
    public void setJoiningDate(LocalDateTime joiningDate) { this.joiningDate = joiningDate; }
    
    public LocalDateTime getPlacedAt() { return placedAt; }
    public void setPlacedAt(LocalDateTime placedAt) { this.placedAt = placedAt; }
}