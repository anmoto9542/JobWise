package com.example.jobWise.controller;

import com.example.jobWise.entity.Resume;
import com.example.jobWise.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/resume")
public class ResumeController extends BaseController {
    @Autowired
    private ResumeService resumeService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadResume(
            @RequestParam Long userId,
            @RequestParam String title,
            @RequestParam(required = false) String content,
            @RequestPart(required = false) MultipartFile file
    ) {
        Resume resume;
        if (file != null) {
            resume = resumeService.uploadFileResume(userId, title, file);
        } else if (content != null) {
            resume = resumeService.uploadTextResume(userId, title, content);
        } else {
            return failure("請提供履歷內容或檔案");
        }
        return success(resume);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResume(@PathVariable Long id) {
        Resume resume = resumeService.getResume(id);
        return success(resume);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam Long userId) {
        List<Resume> resumes = resumeService.getUserResumes(userId);
        return success(resumes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestParam Long userId) throws AccessDeniedException {
        resumeService.deleteResume(id, userId);
        return success();
    }
}
