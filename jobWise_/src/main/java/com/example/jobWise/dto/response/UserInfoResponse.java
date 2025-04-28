package com.example.jobWise.dto.response;

import lombok.Data;

@Data
public class UserInfoResponse {
    private Long userId;
    private String username;
    private String email;
}
