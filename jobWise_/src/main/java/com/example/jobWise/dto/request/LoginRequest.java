package com.example.jobWise.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    @Email(message = "Email格式不正確")
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;
}
