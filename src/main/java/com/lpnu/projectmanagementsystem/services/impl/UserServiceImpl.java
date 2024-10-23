package com.lpnu.projectmanagementsystem.services.impl;

import com.lpnu.projectmanagementsystem.config.JwtProvider;
import com.lpnu.projectmanagementsystem.entities.UserEntity;
import com.lpnu.projectmanagementsystem.exceptions.NotFoundException;
import com.lpnu.projectmanagementsystem.exceptions.UsernameAlreadyExistException;
import com.lpnu.projectmanagementsystem.repositories.UserRepository;
import com.lpnu.projectmanagementsystem.requests.LoginRequest;
import com.lpnu.projectmanagementsystem.responses.AuthResponse;
import com.lpnu.projectmanagementsystem.services.CustomUserDetailsServiceImpl;
import com.lpnu.projectmanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse createUser(UserEntity user) throws Exception {
        UserEntity candidate = userRepository.findByEmail(user.getEmail());
        if (candidate != null) {
            throw new UsernameAlreadyExistException(String.format("Email '%s' already exists", user.getEmail()));
        }

        UserEntity createdUser = new UserEntity();
        createdUser.setFullName(user.getFullName());
        createdUser.setEmail(user.getEmail());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(createdUser.getEmail(), createdUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse res = new AuthResponse(jwt, "Sign up successfully");

        userRepository.save(createdUser);

        return res;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        String username = request.getEmail();
        String password = request.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = JwtProvider.generateToken(authentication);

        return new AuthResponse(jwt, "Sign in successfully");
    }

    @Override
    public UserEntity findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.extractEmail(jwt);
        return findUserByEmail(email);
    }

    @Override
    public UserEntity findUserByEmail(String email) throws Exception {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        return user;
    }

    @Override
    public UserEntity findUserById(Long id) throws Exception {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        return optionalUser.get();
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username!");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password!");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
