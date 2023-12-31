package com.example.miniproject.controller;

import com.example.miniproject.dto.BorrowedBookDTO;
import com.example.miniproject.entity.User;
import com.example.miniproject.response.ResponseHandler;
import com.example.miniproject.service.AuthenticationService;
import com.example.miniproject.service.BorrowedBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/books")
public class BorrowedBookController {
    @Autowired
    private BorrowedBookService borrowedBookService;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/{bookId}/borrow")
    public ResponseEntity<?> borrowBook(@PathVariable int bookId
            , @RequestBody @Valid BorrowedBookDTO borrowedBookDTO) {
        User user = authenticationService.getUserFromToken();
        return ResponseHandler.generateResponse(new Date(),"Successfully borrowed book", HttpStatus.OK,
                borrowedBookService.borrowBook(bookId, user.getId(), borrowedBookDTO));
    }

    @DeleteMapping("/{bookId}/return")
    public ResponseEntity<?> returnBook(@PathVariable int bookId) {
        User user = authenticationService.getUserFromToken();
        borrowedBookService.returnBook(bookId, user.getId());
        return ResponseHandler.generateResponse(new Date(),"Book is returned successfully", HttpStatus.OK);
    }

}
