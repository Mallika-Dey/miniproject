package com.example.miniproject.controller;

import com.example.miniproject.response.ResponseHandler;
import com.example.miniproject.service.AuthenticationService;
import com.example.miniproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;

//    @GetMapping("/{userId}")
//    public ResponseEntity<?> get(@PathVariable int userId) {
//        return ResponseHandler.generateResponse("User details", HttpStatus.OK,
//                userService.getUserDetails(userId));
//    }

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        String mail = authenticationService.getUserMail();
        return ResponseHandler.generateResponse(new Date(), "User details", HttpStatus.OK,
                userService.getUserDetailsByMail(mail));
    }
    
    @GetMapping("/get/all")
    public ResponseEntity<?> getAllUser() {
        return ResponseHandler.generateResponse(new Date(), "User details", HttpStatus.OK,
                userService.getUserDetails());
    }

    @GetMapping("/{userId}/books")
    public ResponseEntity<?> getBookByUserId(@PathVariable int userId) {
        return ResponseHandler.generateResponse(new Date(), "Data fetched successfully", HttpStatus.OK,
                userService.getBookByUserId(userId, authenticationService.getUserFromToken()));
    }

    @GetMapping("/{userId}/borrowed-books")
    public ResponseEntity<?> getBorrowedByBookByUserId(@PathVariable int userId) {
        return ResponseHandler.generateResponse(new Date(), "User borrowed book data fetched successfully", HttpStatus.OK,
                userService.getBorrowedByBookByUserId(userId, authenticationService.getUserFromToken()));
    }


}
