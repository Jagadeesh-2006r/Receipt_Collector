package com.campus.placement.placement_management_system.service;

import com.campus.placement.placement_management_system.model.Application;
import com.campus.placement.placement_management_system.model.Interview;
import com.campus.placement.placement_management_system.repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InterviewService {
    
    @Autowired
    private InterviewRepository interviewRepository;
    
    public List<Interview> getInterviewsByApplication(Application application) {
        return interviewRepository.findByApplication(application);
    }
    
    public List<Interview> getInterviewsByStatus(Interview.InterviewStatus status) {
        return interviewRepository.findByStatus(status);
    }
    
    public Optional<Interview> getInterviewById(Long id) {
        return interviewRepository.findById(id);
    }
    
    public Interview saveInterview(Interview interview) {
        return interviewRepository.save(interview);
    }
    
    public Interview updateInterviewStatus(Long interviewId, Interview.InterviewStatus status) {
        Optional<Interview> interview = interviewRepository.findById(interviewId);
        if (interview.isPresent()) {
            interview.get().setStatus(status);
            return interviewRepository.save(interview.get());
        }
        return null;
    }
}