package com.example.jobWise.service;

import com.example.jobWise.dto.request.LoginRequest;
import com.example.jobWise.dto.request.RegisterRequest;
import com.example.jobWise.dto.response.JwtResponse;
import com.example.jobWise.exception.CustomException;

public interface AuthService {
    void register(RegisterRequest request) throws Exception;

    JwtResponse login(LoginRequest request) throws CustomException;
}
