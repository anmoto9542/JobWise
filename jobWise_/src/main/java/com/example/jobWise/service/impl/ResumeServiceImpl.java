package com.example.jobWise.service.impl;

import com.example.jobWise.dto.response.ResumeDetailResponse;
import com.example.jobWise.dto.response.ResumesResponse;
import com.example.jobWise.entity.Resume;
import com.example.jobWise.enums.StatusCodeEnum;
import com.example.jobWise.exception.CustomException;
import com.example.jobWise.repository.ResumeRepository;
import com.example.jobWise.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            return resumeRepository.save(resume);
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }
    }

    @Override
    public List<ResumesResponse> getUserResumes(Long userId) {
        String key = getResumeCacheKey(userId);
        Map<Object, Object> cached = redisTemplate.opsForHash().entries(key);
        // Redis有存就直接調用
        if (!cached.isEmpty()) {
            return cached.values().stream()
                    .map(obj -> (Resume) obj)
                    .map(resume -> new ResumesResponse(resume.getId(), resume.getTitle()))
                    .collect(Collectors.toList());
        }

        List<Resume> resumes = resumeRepository.findByUserId(userId);
        Map<String, Resume> map = resumes
                .stream()
                .collect(Collectors.toMap(r -> r.getId().toString(), r -> r));

        List<ResumesResponse> result = resumes.stream()
                .map(resume -> new ResumesResponse(resume.getId(), resume.getTitle()))
                .collect(Collectors.toList());

        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.expire(key, cacheTtlMinutes, TimeUnit.MINUTES);

        return result;
    }

    @Override
    public ResumeDetailResponse getResume(Long userId, Long resumeId) {
        String key = getResumeCacheKey(userId);

        Resume resume = (Resume) redisTemplate.opsForHash().get(key, resumeId.toString());

        if (resume == null) { // Redis找不到，從DB撈
            resume = resumeRepository.findById(resumeId)
                    .orElseThrow(() -> new CustomException(StatusCodeEnum.ERR9904));

            redisTemplate.opsForHash().put(key, resumeId.toString(), resume);
            redisTemplate.expire(key, cacheTtlMinutes, TimeUnit.MINUTES);
        }

        return new ResumeDetailResponse(
                resume.getId(),
                resume.getTitle(),
                resume.getContent(),
                resume.getFilePath()
        );
    }


    @Override
    public void deleteResume(Long resumeId, Long userId) throws Exception {
        String key = getResumeCacheKey(userId);

        Resume resume = (Resume) redisTemplate.opsForHash().get(key, resumeId.toString());

        if (resume == null) {
            resume = resumeRepository.findById(resumeId)
                    .orElseThrow(() -> new CustomException(StatusCodeEnum.ERR9904));
        }
        // 刪DB
        resumeRepository.deleteById(resumeId);
        // 刪Redis
        redisTemplate.opsForHash().delete(key, resumeId.toString());
    }

    // 組 Redis_key
    private String getResumeCacheKey(Long userId) {
        return "user:" + userId + ":resumes";
    }
}
