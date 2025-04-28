package com.example.jobWise.dto.request;

import lombok.Data;

@Data
public class InterviewQuestionRequest {
    private String jobTitle; // 職稱
    private String jobDescription; // 職缺描述
}
