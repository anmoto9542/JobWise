package com.example.jobWise.controller;

import com.example.jobWise.dto.request.LoginRequest;
import com.example.jobWise.dto.request.RegisterRequest;
import com.example.jobWise.dto.response.JwtResponse;
import com.example.jobWise.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController extends BaseController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public Object register(@Valid @RequestBody RegisterRequest rq) throws Exception {
        authService.register(rq);
        return success();
    }

    @PostMapping("/login")
    public Object login(@Valid @RequestBody LoginRequest rq) {
        JwtResponse response = authService.login(rq);
        return success();
    }

}
