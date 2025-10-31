package com.campus.placement.placement_management_system.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "interviews")
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;
    
    private String round; // Technical, HR, Final, etc.
    
    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;
    
    private String venue;
    private String interviewerName;
    private String interviewerEmail;
    
    @Enumerated(EnumType.STRING)
    private InterviewStatus status = InterviewStatus.SCHEDULED;
    
    private String feedback;
    private Integer score;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    public enum InterviewStatus {
        SCHEDULED, COMPLETED, CANCELLED, NO_SHOW
    }
}