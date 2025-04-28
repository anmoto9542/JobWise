package com.example.jobWise.service.impl;

import com.example.jobWise.dto.request.InterviewFeedbackRequest;
import com.example.jobWise.dto.request.InterviewQuestionRequest;
import com.example.jobWise.dto.request.SelfIntroRequest;
import com.example.jobWise.service.AIService;
import com.example.jobWise.template.PromptTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    private final OpenAIService openAIService;
    @Override
    public String generateSelfIntro(SelfIntroRequest request) {
        String prompt = String.format(PromptTemplate.SELF_INTRO_PROMPT, request.getResumeContent(), request.getJobDescription());
        return openAIService.chat(prompt, "gpt-4");
    }
    @Override
    public String generateInterviewQuestion(InterviewQuestionRequest request) {
        String prompt = String.format(PromptTemplate.INTERVIEW_ASK_PROMPT, request.getJobTitle(), request.getJobDescription());
        return openAIService.chat(prompt, "gpt-4");
    }
    @Override
    public String generateInterviewFeedback(InterviewFeedbackRequest request) {
        String prompt = String.format(PromptTemplate.INTERVIEW_FEEDBACK_PROMPT, request.getAnswer());
        return openAIService.chat(prompt, "gpt-4");
    }
}
