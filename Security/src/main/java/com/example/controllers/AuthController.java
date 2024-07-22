package com.example.controllers;

import com.example.dtos.JwtRequestDTO;
import com.example.dtos.JwtResponseDTO;
import com.example.exceptions.AppError;
import com.example.services.UserService;
import com.example.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDTO jwtRequestDTO) {
        String username = jwtRequestDTO.getUsername();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, jwtRequestDTO.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect login or password"), HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userService.loadUserByUsername(username);
        String token = jwtUtils.generateToken(userDetails);

        return new ResponseEntity<>(new JwtResponseDTO(token), HttpStatus.ACCEPTED);
     }

}
