package com.example.jobWise.controller;

import com.example.jobWise.dto.response.UserInfoResponse;
import com.example.jobWise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public Object getUserInfo(@AuthenticationPrincipal String username) {
        UserInfoResponse userInfo = userService.getUserInfo(username);
        return success(userInfo);
    }
}
