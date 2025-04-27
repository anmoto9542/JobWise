package com.example.jobWise.service.impl;

import com.example.jobWise.dto.request.LoginRequest;
import com.example.jobWise.dto.request.RegisterRequest;
import com.example.jobWise.dto.response.JwtResponse;
import com.example.jobWise.entity.User;
import com.example.jobWise.repository.UserRepository;
import com.example.jobWise.security.JwtTokenProvider;
import com.example.jobWise.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtProvider;

    @Override
    public void register(RegisterRequest request) throws Exception {

        // 確認使用者是否已存在
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new Exception();
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtProvider.generateToken(user.getUsername());
        return new JwtResponse(token);
    }

}
