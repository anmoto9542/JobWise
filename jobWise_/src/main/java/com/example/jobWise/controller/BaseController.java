package com.example.jobWise.controller;

import com.example.jobWise.dto.response.ApiResponse;
import com.example.jobWise.enums.StatusCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class BaseController {

    protected ResponseEntity<ApiResponse<Object>> success() {
        return ResponseEntity.ok(new ApiResponse<>(true, StatusCodeEnum.SUCCESS.getMessage(), null));
    }

    protected <T> ResponseEntity<ApiResponse<T>> successLogin(T data) {
        return ResponseEntity.ok().body(new ApiResponse<>(true, StatusCodeEnum.SUCCESS.getMessage(), data));
    }

    protected <T> ResponseEntity<ApiResponse<T>> success(T data) {
        String newToken = getNewTokenFromRequest();
        ApiResponse<T> response = new ApiResponse<>(true, StatusCodeEnum.SUCCESS.getMessage(), data, newToken);
        return ResponseEntity.ok()
                .header("AuthToken", newToken != null ? newToken : "")
                .body(response);
    }

    protected ResponseEntity<ApiResponse<Object>> failure(String message) {
        String newToken = getNewTokenFromRequest();
        return ResponseEntity.badRequest()
                .header("AuthToken", newToken != null ? newToken : "")
                .body(new ApiResponse<>(false, message, null, newToken != null ? newToken : ""));
    }

    protected String getNewTokenFromRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Object token = request.getAttribute("AuthToken");
            if (token instanceof String) {
                return (String) token;
            }
        }
        return null;
    }

}
