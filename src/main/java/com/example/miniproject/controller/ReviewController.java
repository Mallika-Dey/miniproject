package com.example.miniproject.controller;

import com.example.miniproject.dto.ReviewDTO;
import com.example.miniproject.response.ResponseHandler;
import com.example.miniproject.service.AuthenticationService;
import com.example.miniproject.service.ReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class ReviewController {
    private ReviewService reviewService;
    private AuthenticationService authenticationService;

    @PostMapping("/{bookId}/reviews/create")
    public ResponseEntity<?> add(@PathVariable int bookId,
                                 @RequestBody @Valid ReviewDTO reviewDTO) {
        reviewService.createReview(bookId, authenticationService.getUserFromToken().getId(), reviewDTO);
        return ResponseHandler.generateResponse(new Date(), "Review is added", HttpStatus.OK);
    }

    @PutMapping("/reviews/{reviewId}/update")
    public ResponseEntity<?> update(@PathVariable int reviewId,
                                    @RequestBody @Valid ReviewDTO reviewDTO) {
        reviewService.updateReview(authenticationService.getUserFromToken().getId()
                , reviewId, reviewDTO);
        return ResponseHandler.generateResponse(new Date(), "Review is updated", HttpStatus.OK);
    }

    @DeleteMapping("/reviews/{reviewId}/delete")
    public ResponseEntity<?> delete(@PathVariable int reviewId) {
        reviewService.deleteReview(reviewId, authenticationService.getUserFromToken().getId());
        return ResponseHandler.generateResponse(new Date(), "Review is deleted successfully", HttpStatus.OK);
    }

    @GetMapping("{bookId}/reviews")
    public ResponseEntity<?> get(@PathVariable int bookId) {
        return ResponseHandler.generateResponse(new Date(), "Book Reviews",
                HttpStatus.OK, reviewService.getReviewByBookId(bookId));
    }
}
