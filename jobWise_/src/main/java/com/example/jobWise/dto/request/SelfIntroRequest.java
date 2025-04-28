package com.example.jobWise.dto.request;

import lombok.Data;

@Data
public class SelfIntroRequest {
    private String resumeContent; // 履歷摘要
    private String jobDescription; // 職缺描述
}
