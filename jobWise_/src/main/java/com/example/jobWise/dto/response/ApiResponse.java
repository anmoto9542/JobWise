package com.example.jobWise.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ApiResponse<T> {
    private boolean success;
    private String message;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private T data;
    private String authToken;

    public ApiResponse(boolean success, String message, T data) {
        this(success, message, data, null);
    }

    public ApiResponse(boolean success, String message, T data, String authToken) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.authToken = authToken;
    }
}
