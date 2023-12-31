package com.example.miniproject.service;

import com.example.miniproject.dto.ReviewDTO;
import com.example.miniproject.dto.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {
    public void createReview(int bookId, int userId, ReviewDTO reviewDTO);

    public void updateReview(int userId, int reviewId, ReviewDTO reviewDTO);

    public void deleteReview(int reviewId, int userId);

    public List<ReviewResponseDTO> getReviewByBookId(int bookId);
}
