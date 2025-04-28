package com.example.jobWise.controller;

import com.example.jobWise.dto.request.UploadResumeRequest;
import com.example.jobWise.dto.response.ResumeDetailResponse;
import com.example.jobWise.dto.response.ResumesResponse;
import com.example.jobWise.entity.Resume;
import com.example.jobWise.enums.StatusCodeEnum;
import com.example.jobWise.exception.CustomException;
import com.example.jobWise.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/resume")
public class ResumeController extends BaseController {
    @Autowired
    private ResumeService resumeService;

    /**
     * 上傳履歷
     *
     * @return
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadResume(
            @ModelAttribute UploadResumeRequest request) {

        Resume resume;
        if (request.getFile() != null) {
            resume = resumeService.uploadFileResume(request.getUserId(), request.getTitle(), request.getFile());
        } else if (request.getContent() != null) {
            resume = resumeService.uploadTextResume(request.getUserId(), request.getTitle(), request.getContent());
        } else {
            return failure(StatusCodeEnum.ERR9905.getMessage());
        }
        return success(resume);
    }

    /**
     * 查使用者全部履歷
     *
     * @param userId
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam Long userId) {
        try {
            List<ResumesResponse> rs = resumeService.getUserResumes(userId);
            return success(rs);
        } catch (CustomException ce) {
            return failure(ce.getMessage());
        } catch (Exception e) {
            return failure(StatusCodeEnum.ERR9999.getMessage());
        }
    }

    /**
     * 查看履歷內容
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getResume(@PathVariable Long id, Long userId) {
        try {
            ResumeDetailResponse rs = resumeService.getResume(userId, id);
            return success(rs);
        } catch (CustomException ce) {
            return failure(ce.getMessage());
        } catch (Exception e) {
            return failure(StatusCodeEnum.ERR9999.getMessage());
        }

    }


    /**
     * 刪除履歷
     *
     * @param id
     * @param userId
     * @return
     * @throws AccessDeniedException
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestParam Long userId) throws Exception {
        try {
            resumeService.deleteResume(id, userId);
            return success();
        } catch (CustomException ce) {
            return failure(ce.getMessage());
        } catch (Exception e) {
            return failure(StatusCodeEnum.ERR9999.getMessage());
        }
    }
}
