package com.placement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "interviews")
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interviewId;
    
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;
    
    private Integer roundNumber;
    private String roundType;
    private LocalDateTime interviewDate;
    private String location;
    private String meetingLink;
    
    @Enumerated(EnumType.STRING)
    private InterviewResult result;
    
    private String feedback;
    private Integer score;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    public enum InterviewResult {
        SCHEDULED, COMPLETED, PASSED, FAILED, NO_SHOW
    }
    
    // Constructors
    public Interview() {}
    
    public Interview(Job job, User student, Integer roundNumber, LocalDateTime interviewDate) {
        this.job = job;
        this.student = student;
        this.roundNumber = roundNumber;
        this.interviewDate = interviewDate;
        this.result = InterviewResult.SCHEDULED;
    }
    
    // Getters and Setters
    public Long getInterviewId() { return interviewId; }
    public void setInterviewId(Long interviewId) { this.interviewId = interviewId; }
    
    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }
    
    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }
    
    public Integer getRoundNumber() { return roundNumber; }
    public void setRoundNumber(Integer roundNumber) { this.roundNumber = roundNumber; }
    
    public String getRoundType() { return roundType; }
    public void setRoundType(String roundType) { this.roundType = roundType; }
    
    public LocalDateTime getInterviewDate() { return interviewDate; }
    public void setInterviewDate(LocalDateTime interviewDate) { this.interviewDate = interviewDate; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getMeetingLink() { return meetingLink; }
    public void setMeetingLink(String meetingLink) { this.meetingLink = meetingLink; }
    
    public InterviewResult getResult() { return result; }
    public void setResult(InterviewResult result) { this.result = result; }
    
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}