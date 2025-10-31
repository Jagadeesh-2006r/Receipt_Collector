package com.campus.placement.placement_management_system.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "placements")
public class Placement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;
    
    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;
    
    private Double offerSalary;
    private String offerLetter;
    
    @Column(name = "placed_at")
    private LocalDateTime placedAt = LocalDateTime.now();
    
    private boolean accepted = false;
}