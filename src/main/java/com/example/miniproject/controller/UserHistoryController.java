package com.example.miniproject.controller;

import com.example.miniproject.dto.UserRequestDTO;
import com.example.miniproject.entity.User;
import com.example.miniproject.response.ResponseHandler;
import com.example.miniproject.service.AuthenticationService;
import com.example.miniproject.service.UserHistoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/users")
public class UserHistoryController {
    @Autowired
    private UserHistoryService userHistoryService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/history")
    public ResponseEntity<?> getHistory() {
    	User user = authenticationService.getUserFromToken();
        return ResponseHandler.generateResponse(new Date(),"User Histories",
                HttpStatus.OK,
                userHistoryService.viewHistory(user.getId(), user));
    }
    
    @GetMapping("/{userId}/history")
    public ResponseEntity<?> getHistoryByAdmin(@PathVariable int userId) {
        return ResponseHandler.generateResponse(new Date(),"User Histories",
                HttpStatus.OK,
                userHistoryService.viewHistory(userId, authenticationService.getUserFromToken()));
    }
}
