package com.example.miniproject.controller;

import com.example.miniproject.response.ResponseHandler;
import com.example.miniproject.service.AuthenticationService;
import com.example.miniproject.service.ReservedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/books")
public class ReservedBookController {
    @Autowired
    private ReservedBookService reservedBookService;
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/{bookId}/reserve")
    public ResponseEntity<?> reserveReservation(@PathVariable int bookId) {
        reservedBookService.bookReserve(bookId, authenticationService.getUserFromToken().getId());
        return ResponseHandler.generateResponse(new Date(), "Book is reserved", HttpStatus.CREATED);
    }

    @DeleteMapping("/{bookId}/cancel")
    public ResponseEntity<?> cancelReservation(@PathVariable int bookId) {
        reservedBookService.cancelReservation(bookId, authenticationService.getUserFromToken().getId());
        return ResponseHandler.generateResponse(new Date(), "Reservation is cancel", HttpStatus.OK);
    }
}
