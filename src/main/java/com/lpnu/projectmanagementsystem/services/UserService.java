package com.lpnu.projectmanagementsystem.services;

import com.lpnu.projectmanagementsystem.entities.UserEntity;
import com.lpnu.projectmanagementsystem.requests.LoginRequest;
import com.lpnu.projectmanagementsystem.responses.AuthResponse;

public interface UserService {
    AuthResponse createUser(UserEntity user) throws Exception;
    AuthResponse login(LoginRequest request);

    UserEntity findUserProfileByJwt(String jwt) throws Exception;
    UserEntity findUserByEmail(String email) throws Exception;
    UserEntity findUserById(Long id) throws Exception;

    UserEntity updateUserCountOfProjects(UserEntity user, int num);
}
