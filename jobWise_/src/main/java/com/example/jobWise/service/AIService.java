package com.example.jobWise.service;

import com.example.jobWise.dto.request.InterviewFeedbackRequest;
import com.example.jobWise.dto.request.InterviewQuestionRequest;
import com.example.jobWise.dto.request.SelfIntroRequest;

public interface AIService {
    String generateSelfIntro(SelfIntroRequest request);
    String generateInterviewQuestion(InterviewQuestionRequest request);
    String generateInterviewFeedback(InterviewFeedbackRequest request);
}
