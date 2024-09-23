package com.lpnu.projectmanagementsystem.controllers;

import com.lpnu.projectmanagementsystem.entities.UserEntity;
import com.lpnu.projectmanagementsystem.requests.LoginRequest;
import com.lpnu.projectmanagementsystem.responses.AuthResponse;
import com.lpnu.projectmanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("sign-up")
    public ResponseEntity<AuthResponse> signUp(@RequestBody UserEntity user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body(new AuthResponse("", e.getMessage()));
        }
    }

    @PostMapping("sign-in")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.login(request));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST).body(new AuthResponse("", e.getMessage()));
        }
    }
}
