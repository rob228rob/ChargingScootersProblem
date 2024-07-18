package com.example.dtos;

import lombok.Data;

@Data
public class RegistrationUserInfo {
    private String username;

    private String password;

    private String confirmPassword;

    private String email;
}
