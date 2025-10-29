package com.placement.service;

import com.placement.entity.Interview;
import com.placement.entity.Job;
import com.placement.entity.User;
import com.placement.repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InterviewService {
    
    @Autowired
    private InterviewRepository interviewRepository;
    
    @Autowired
    private JobService jobService;
    
    @Autowired
    private UserService userService;
    
    public Interview scheduleInterview(Long jobId, Long studentId, Integer roundNumber, 
                                     LocalDateTime interviewDate, String roundType, String location) {
        Optional<Job> jobOpt = jobService.getJobById(jobId);
        if (jobOpt.isEmpty()) {
            throw new IllegalArgumentException("Job not found");
        }
        
        Job job = jobOpt.get();
        User student = userService.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        
        Interview interview = new Interview(job, student, roundNumber, interviewDate);
        interview.setRoundType(roundType);
        interview.setLocation(location);
        
        return interviewRepository.save(interview);
    }
    
    public List<Interview> getInterviewsByJob(Job job) {
        return interviewRepository.findByJob(job);
    }
    
    public List<Interview> getInterviewsByStudent(User student) {
        return interviewRepository.findByStudent(student);
    }
    
    public List<Interview> getUpcomingInterviewsForStudent(Long studentId) {
        return interviewRepository.findUpcomingInterviewsForStudent(studentId);
    }
    
    public List<Interview> getUpcomingInterviewsForStudent(User student) {
        return interviewRepository.findUpcomingInterviewsForStudent(student.getUserId());
    }
    
    public Interview updateInterviewResult(Long interviewId, Interview.InterviewResult result, 
                                         String feedback, Integer score) {
        Optional<Interview> interviewOpt = interviewRepository.findById(interviewId);
        if (interviewOpt.isEmpty()) {
            throw new IllegalArgumentException("Interview not found");
        }
        
        Interview interview = interviewOpt.get();
        interview.setResult(result);
        if (feedback != null) {
            interview.setFeedback(feedback);
        }
        if (score != null) {
            interview.setScore(score);
        }
        
        return interviewRepository.save(interview);
    }
    
    public List<Interview> getInterviewsForDateRange(LocalDateTime start, LocalDateTime end) {
        return interviewRepository.findInterviewsBetweenDates(start, end);
    }
    
    public List<Interview> getInterviewsByJobAndRound(Long jobId, Integer roundNumber) {
        return interviewRepository.findByJobAndRound(jobId, roundNumber);
    }
    
    public Optional<Interview> getInterviewById(Long id) {
        return interviewRepository.findById(id);
    }
    
    public List<Interview> getScheduledInterviews() {
        return interviewRepository.findByResult(Interview.InterviewResult.SCHEDULED);
    }
    
    public Interview updateInterview(Interview interview) {
        return interviewRepository.save(interview);
    }
    
    public Interview saveInterview(Interview interview) {
        return interviewRepository.save(interview);
    }
}