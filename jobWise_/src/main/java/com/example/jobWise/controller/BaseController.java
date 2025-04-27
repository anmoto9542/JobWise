package com.example.jobWise.controller;

import com.example.jobWise.dto.response.ApiResponse;
import com.example.jobWise.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class BaseController {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    protected ResponseEntity<ApiResponse<Object>> success() {
        return success(null, null);
    }

    protected <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Success", data));
    }

    protected <T> ResponseEntity<ApiResponse<T>> success(T data, String authToken) {
        String newToken = jwtTokenProvider.refreshToken(authToken);
        return ResponseEntity.ok(new ApiResponse<>(true, "Success", data, authToken));
    }

    protected ResponseEntity<ApiResponse<Object>> failure(String message) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(false, message, null));
    }

}
