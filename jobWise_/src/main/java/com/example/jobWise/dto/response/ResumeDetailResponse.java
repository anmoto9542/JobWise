package com.example.jobWise.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDetailResponse {
    private Long id;
    private String title;
    private String content;
    private String filePath;
}
