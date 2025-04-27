package com.example.jobWise.service.impl;

import com.example.jobWise.dto.request.LoginRequest;
import com.example.jobWise.dto.request.RegisterRequest;
import com.example.jobWise.dto.response.JwtResponse;
import com.example.jobWise.entity.User;
import com.example.jobWise.enums.StatusCodeEnum;
import com.example.jobWise.exception.CustomException;
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

        try {
            // 確認使用者是否已存在
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new CustomException(StatusCodeEnum.ERR9901);
            }

            User user = new User();
            user.setEmail(request.getEmail());
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public JwtResponse login(LoginRequest request) throws CustomException {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new CustomException(StatusCodeEnum.ERR9902));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(StatusCodeEnum.ERR9902);
        }

        String token = jwtProvider.generateToken(user.getEmail());
        return new JwtResponse(token);
    }

}
