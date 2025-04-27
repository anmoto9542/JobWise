package com.example.jobWise.service;

import com.example.jobWise.entity.Resume;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface ResumeService {
    Resume uploadTextResume(Long userId, String title, String content);
    Resume uploadFileResume(Long userId, String title, MultipartFile file);
    Resume getResume(Long resumeId);
    List<Resume> getUserResumes(Long userId);
    void deleteResume(Long resumeId, Long userId) throws AccessDeniedException;
}
