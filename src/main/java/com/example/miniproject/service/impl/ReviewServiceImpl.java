package com.example.miniproject.service.impl;

import com.example.miniproject.dto.ReviewDTO;
import com.example.miniproject.dto.ReviewResponseDTO;
import com.example.miniproject.entity.Book;
import com.example.miniproject.entity.Review;
import com.example.miniproject.entity.User;
import com.example.miniproject.exception.CustomException;
import com.example.miniproject.repository.BookRepository;
import com.example.miniproject.repository.ReviewRepository;
import com.example.miniproject.repository.UserRepository;
import com.example.miniproject.service.ReviewService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public void createReview(int bookId, int userId, ReviewDTO reviewDTO) {
        if (reviewRepository.existsByUserIdAndBookId(userId, bookId)) {
            throw new CustomException("You are not allowed to review again", HttpStatus.CONFLICT);
        }

        Book book = bookRepository.findByIdAndIsDeletedFalse(bookId)
                .orElseThrow(() -> new CustomException("Book does not exist", HttpStatus.NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User id not exist", HttpStatus.NOT_FOUND));

        Review review = modelMapper.map(reviewDTO, Review.class);
        review.setUser(user);
        review.setBook(book);

        reviewRepository.save(review);
        updateBookRating(book);
    }

    @Override
    public void updateReview(int userId, int reviewId, ReviewDTO reviewDTO) {
        Review review = getReviewById(reviewId);
        if (review.getUser().getId() != userId) {
            throw new CustomException("You didn't give this review", HttpStatus.NOT_FOUND);
        }
        if (review.getBook().isDeleted()) {
            throw new CustomException("Book is not available", HttpStatus.NOT_FOUND);
        }
        review.setReviewText(reviewDTO.getReviewText());
        review.setRating(reviewDTO.getRating());
        reviewRepository.save(review);
        updateBookRating(review.getBook());
    }

    @Override
    public void deleteReview(int reviewId, int userId) {
        Review review = getReviewById(reviewId);
        if (review.getUser().getId() != userId) {
            throw new CustomException("You didn't give this review", HttpStatus.NOT_FOUND);
        }
        if (review.getBook().isDeleted()) {
            throw new CustomException("Book is not available. You can't modify/delete review"
                    , HttpStatus.NOT_FOUND);
        }
        reviewRepository.deleteById(reviewId);
        updateBookRating(review.getBook());
    }

    @Override
    public List<ReviewResponseDTO> getReviewByBookId(int bookId) {
        Book book = bookRepository.findByIdAndIsDeletedFalse(bookId)
                .orElseThrow(() -> new CustomException("Book id not exist", HttpStatus.NOT_FOUND));

        return reviewRepository.findAllByBookId(bookId).stream()
                .map(this::mapToReviewResponse).toList();
    }

    private ReviewResponseDTO mapToReviewResponse(Review review) {
        return ReviewResponseDTO
                .builder()
                .reviewId(review.getReviewId())
                .reviewText(review.getReviewText())
                .rating(review.getRating())
                .userName(review.getUser().getFirstName() + " " + review.getUser().getLastName())
                .build();
    }

    private Review getReviewById(int reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException("Review not found", HttpStatus.NOT_FOUND));
    }

    private void updateBookRating(Book book) {
        double averageRating = book.getReview()
                .stream().mapToDouble(Review::getRating)
                .average().orElse(0.0);
        book.setRating(averageRating);
        bookRepository.save(book);
    }
}
