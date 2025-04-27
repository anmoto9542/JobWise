package com.example.jobWise.service.impl;

import com.example.jobWise.dto.response.UserInfoResponse;
import com.example.jobWise.entity.User;
import com.example.jobWise.enums.StatusCodeEnum;
import com.example.jobWise.exception.CustomException;
import com.example.jobWise.repository.UserRepository;
import com.example.jobWise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserInfoResponse getUserInfo(String email) throws CustomException{
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(StatusCodeEnum.ERR9903));

        UserInfoResponse userInfo = new UserInfoResponse();
        userInfo.setEmail(user.getEmail());
        userInfo.setUsername(user.getUsername());

        return userInfo;
    }
}
