package com.lpnu.projectmanagementsystem.controllers;

import com.lpnu.projectmanagementsystem.entities.UserEntity;
import com.lpnu.projectmanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("profile")
    public ResponseEntity<UserEntity> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
