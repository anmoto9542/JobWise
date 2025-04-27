package com.example.jobWise.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    @Email(message = "Email格式不正確")
    private String email;

    @NotBlank
    @Size(min = 8, message = "密碼長度至少8個字元")
    private String password;

    @NotBlank
    private String username;
}
