package com.example.jobWise.controller;

import com.example.jobWise.dto.request.LoginRequest;
import com.example.jobWise.dto.request.RegisterRequest;
import com.example.jobWise.dto.response.JwtResponse;
import com.example.jobWise.enums.StatusCodeEnum;
import com.example.jobWise.exception.CustomException;
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

    /**
     * 建立帳戶
     *
     * @param rq
     * @return
     * @throws Exception
     */
    @PostMapping("/register")
    public Object register(@Valid @RequestBody RegisterRequest rq) throws Exception {
        try {
            authService.register(rq);
            return success();
        } catch (CustomException ce) {
            return failure(ce.getMessage());
        } catch (Exception e) {
            return failure(StatusCodeEnum.ERR9999.getMessage());
        }
    }

    /**
     * 登入
     *
     * @param rq
     * @return
     */
    @PostMapping("/login")
    public Object login(@Valid @RequestBody LoginRequest rq) {
        try {
            JwtResponse response = authService.login(rq);
            return successLogin(response);
        } catch (CustomException ce) {
            return failure(ce.getMessage());
        } catch (Exception e) {
            return failure(StatusCodeEnum.ERR9999.getMessage());
        }
    }

}
