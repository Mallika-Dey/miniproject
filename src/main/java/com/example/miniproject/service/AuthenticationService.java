package com.example.miniproject.service;

import com.example.miniproject.auth.AuthenticationRequest;
import com.example.miniproject.auth.AuthenticationResponse;
import com.example.miniproject.dto.UserRequestDTO;
import com.example.miniproject.entity.Role;
import com.example.miniproject.entity.User;
import com.example.miniproject.exception.CustomException;
import com.example.miniproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(UserRequestDTO request) {
        boolean isPresent = userRepository.findByEmail(request.getEmail()).isPresent();
        if (isPresent) {
            throw new CustomException("Email should be unique", HttpStatus.BAD_REQUEST);
        }
        var user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .address(request.getAddress())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .role(user.getRole().toString())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );
        var user = userRepository.findByEmail(request.getEmail()).get();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .role(user.getRole().toString())
                .build();
    }

    public User getUserFromToken() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new CustomException("User not found for email: " + email, HttpStatus.NOT_FOUND));
    }

    public String getUserMail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
