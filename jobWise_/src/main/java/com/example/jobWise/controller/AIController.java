package com.example.jobWise.controller;

import com.example.jobWise.dto.request.InterviewFeedbackRequest;
import com.example.jobWise.dto.request.InterviewQuestionRequest;
import com.example.jobWise.dto.request.SelfIntroRequest;
import com.example.jobWise.dto.response.ApiResponse;
import com.example.jobWise.enums.StatusCodeEnum;
import com.example.jobWise.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AIController extends BaseController {
    @Autowired
    private AIService AIService;

    /**
     * 生成自我介紹
     *
     * @param request
     * @return
     */
    @PostMapping("/self-intro")
    public ResponseEntity<?> generateSelfIntro(@RequestBody SelfIntroRequest request) {
        try {
            String result = AIService.generateSelfIntro(request);
            return success(result);
        } catch (Exception e) {
            return failure(StatusCodeEnum.ERR9999.getMessage());
        }
    }

    /**
     * 模擬面試問答
     *
     * @param request
     * @return
     */
    @PostMapping("/interview/ask")
    public ResponseEntity<?> generateInterviewQuestion(@RequestBody InterviewQuestionRequest request) {
        try {
            String result = AIService.generateInterviewQuestion(request);
            return success(result);
        } catch (Exception e) {
            return failure(StatusCodeEnum.ERR9999.getMessage());
        }
    }

    /**
     * 問答回饋
     *
     * @param request
     * @return
     */
    @PostMapping("/interview/feedback")
    public ResponseEntity<?> generateInterviewFeedback(@RequestBody InterviewFeedbackRequest request) {
        try {
            String result = AIService.generateInterviewFeedback(request);
            return success(result);
        } catch (Exception e) {
            return failure(StatusCodeEnum.ERR9999.getMessage());
        }
    }
}
