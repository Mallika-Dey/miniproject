package com.example.miniproject.service.impl;

import com.example.miniproject.entity.Book;
import com.example.miniproject.entity.ReservedBook;
import com.example.miniproject.entity.User;
import com.example.miniproject.exception.CustomException;
import com.example.miniproject.repository.BookRepository;
import com.example.miniproject.repository.ReservedBookRepository;
import com.example.miniproject.repository.UserRepository;
import com.example.miniproject.service.ReservedBookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class ReservedBookServiceImpl implements ReservedBookService {
    private ReservedBookRepository reservedBookRepository;
    private BookRepository bookRepository;
    private UserRepository userRepository;

    @Override
    public void bookReserve(int bookId, int userId) {
        Book book = getBook(bookId);
        User user = getUser(userId);

        if (book.isAvailable()) {
            throw new CustomException("Reservation Failed: " +
                    "Book is available now. You may borrow book", HttpStatus.CONFLICT);
        }

        if (book.getBorrowedBook().getUser().getId() == userId) {
            throw new CustomException("You have already borrowed this book", HttpStatus.CONFLICT);
        }

        if (reservedBookRepository.existsByUserIdAndBookId(userId, bookId)) {
            throw new CustomException("You have already reserved", HttpStatus.CONFLICT);
        }

        ReservedBook reservedBook = new ReservedBook();
        reservedBook.setReservationDate(new Date());
        reservedBook.setBook(book);
        reservedBook.setUser(user);
        reservedBookRepository.save(reservedBook);
    }

    @Override
    public void cancelReservation(int bookId, int userId) {
        Book book = getBook(bookId);
        User user = getUser(userId);

        ReservedBook reservedBook = book.getReservedBooks().stream()
                .filter(reservedBook1 -> reservedBook1.getUser().getId() == userId)
                .findFirst()
                .orElseThrow(() -> new CustomException("You didn't reserved the book", HttpStatus.BAD_REQUEST));

        reservedBookRepository.delete(reservedBook);
    }

    private Book getBook(int bookId) {
        return bookRepository.findByIdAndIsDeletedFalse(bookId)
                .orElseThrow(() -> new CustomException("Book doesn't exist", HttpStatus.NOT_FOUND));
    }

    private User getUser(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("Invalid User", HttpStatus.NOT_FOUND));
    }
}
