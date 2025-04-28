package com.example.jobWise.service;

import com.example.jobWise.dto.response.ResumeDetailResponse;
import com.example.jobWise.dto.response.ResumesResponse;
import com.example.jobWise.entity.Resume;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {
    Resume uploadTextResume(Long userId, String title, String content);

    Resume uploadFileResume(Long userId, String title, MultipartFile file);

    List<ResumesResponse> getUserResumes(Long userId);

    ResumeDetailResponse getResume(Long userId, Long resumeId);

    void deleteResume(Long resumeId, Long userId) throws Exception;
}
