package com.example.dtos;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class JwtResponseDTO implements Serializable {
    private String token;
}
