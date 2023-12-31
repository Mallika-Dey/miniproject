package com.example.miniproject.controller;

import com.example.miniproject.auth.AuthenticationRequest;
import com.example.miniproject.auth.AuthenticationResponse;
import com.example.miniproject.dto.UserRequestDTO;
import com.example.miniproject.response.ResponseHandler;
import com.example.miniproject.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequestDTO request) {
       return ResponseHandler.generateResponse(new Date(),"Registration successful"
               , HttpStatus.CREATED, authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseHandler.generateResponse(new Date(),"Login successful"
                , HttpStatus.OK, authenticationService.authenticate(request));
    }
}
