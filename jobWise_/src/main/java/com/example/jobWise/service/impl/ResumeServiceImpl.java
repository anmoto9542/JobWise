package com.example.jobWise.service.impl;

import com.example.jobWise.entity.Resume;
import com.example.jobWise.repository.ResumeRepository;
import com.example.jobWise.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${resume.upload.path}")
    private String uploadPath;

    @Value("${resume.cache.ttl-minutes}")
    private long cacheTtlMinutes;

    @Override
    public Resume uploadTextResume(Long userId, String title, String content) {
        Resume resume = new Resume();
        resume.setUserId(userId);
        resume.setTitle(title);
        resume.setContent(content);
        resume.setCreatedAt(LocalDateTime.now());
        return resumeRepository.save(resume);
    }

    @Override
    public Resume uploadFileResume(Long userId, String title, MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadPath, fileName);
            Files.write(filePath, file.getBytes());

            Resume resume = new Resume();
            resume.setUserId(userId);
            resume.setTitle(title);
            resume.setFilePath(filePath.toString());
            resume.setCreatedAt(LocalDateTime.now());
            return resumeRepository.save(resume);
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }
    }

    @Override
    public Resume getResume(Long resumeId) {
        return resumeRepository.findById(resumeId).orElseThrow(() -> new RuntimeException("Resume not found"));
    }

    @Override
    public List<Resume> getUserResumes(Long userId) {
        String key = getResumeCacheKey(userId);
        Map<Object, Object> cached = redisTemplate.opsForHash().entries(key);

        if (!cached.isEmpty()) {
            return cached.values().stream()
                    .map(obj -> (Resume) obj)
                    .collect(Collectors.toList());
        }

        List<Resume> resumes = resumeRepository.findByUserId(userId);
        Map<String, Resume> map = resumes.stream()
                .collect(Collectors.toMap(r -> r.getId().toString(), r -> r));

        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.expire(key, cacheTtlMinutes, TimeUnit.MINUTES);

        return resumes;
    }

    @Override
    public void deleteResume(Long resumeId, Long userId) throws AccessDeniedException {
        Resume resume = getResume(resumeId);
        if (!resume.getUserId().equals(userId)) {
            throw new AccessDeniedException("Not allowed");
        }
        resumeRepository.delete(resume);
    }

    private String getResumeCacheKey(Long userId) {
        return "user:" + userId + ":resumes";
    }
}
