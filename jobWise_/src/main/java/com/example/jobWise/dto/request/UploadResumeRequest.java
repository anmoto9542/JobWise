package com.example.jobWise.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadResumeRequest {
    private Long userId;
    private String title;
    private String content;
    private MultipartFile file;
}