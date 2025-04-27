package com.example.jobWise.service;

import com.example.jobWise.dto.response.UserInfoResponse;

public interface UserService {
    UserInfoResponse getUserInfo(String email);
}
