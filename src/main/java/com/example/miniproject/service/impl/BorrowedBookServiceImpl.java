package com.example.miniproject.service.impl;

import com.example.miniproject.dto.BorrowedBookDTO;
import com.example.miniproject.dto.BorrowedBookResponseDTO;
import com.example.miniproject.entity.*;
import com.example.miniproject.exception.CustomException;
import com.example.miniproject.repository.*;
import com.example.miniproject.service.BorrowedBookService;
import com.example.miniproject.service.EmailService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class BorrowedBookServiceImpl implements BorrowedBookService {
    private BorrowedBookRepository borrowedBookRepository;
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private UserHistoryRepository userHistoryRepository;
    private ReservedBookRepository reservedBookRepository;
    private ModelMapper modelMapper;

    private EmailService emailService;

    @Override
    public BorrowedBookResponseDTO borrowBook(int bookId, int userId, BorrowedBookDTO borrowedBookDTO) {
        Book book = getBook(bookId);
        User user = getUser(userId);
        if (!book.isAvailable()) {
            throw new CustomException("Book is not available for borrowed", HttpStatus.CONFLICT);
        }

        book.setAvailable(false);
        BorrowedBook borrowedBook = modelMapper.map(borrowedBookDTO, BorrowedBook.class);
        borrowedBook.setBorrowedDate(new Date());
        borrowedBook.setBook(book);
        borrowedBook.setUser(user);

        borrowedBookRepository.save(borrowedBook);
        bookRepository.save(book);

        //update user history table
        UserHistory userHistory = new UserHistory();
        userHistory.setBorrowedDate(borrowedBook.getBorrowedDate());
        userHistory.setDueDate(borrowedBook.getDueDate());
        userHistory.setUser(borrowedBook.getUser());
        userHistory.setBook(borrowedBook.getBook());
        userHistoryRepository.save(userHistory);
        //if user previously reserved the book
        reservedBookRepository.findByUserIdAndBookId(userId, bookId).ifPresent(reservedBook ->
                reservedBookRepository.delete(reservedBook));

        return new BorrowedBookResponseDTO(
                book.getTitle(), user.getFirstName(), borrowedBook.getBorrowedDate(), borrowedBookDTO.getDueDate()
        );
    }

    @Override
    public void returnBook(int bookId, int userId) {
        Book book = getBook(bookId);
        User user = getUser(userId);

        if (book.isAvailable() || book.getBorrowedBook().getUser().getId() != userId) {
            throw new CustomException("You didn't borrowed the book", HttpStatus.BAD_REQUEST);
        }

        book.setAvailable(true);
        bookRepository.save(book);

        borrowedBookRepository.deleteByBookId(bookId);

        //update user history table
        UserHistory userHistory = userHistoryRepository.findLastByBookId(bookId);
        userHistory.setReturnDate(new Date());
        userHistoryRepository.save(userHistory);

        for (ReservedBook reserve : reservedBookRepository.findByBookId(bookId)) {
            emailService.sendSimpleMail(new EmailDetails(reserve.getUser().getEmail()
                    , "Hello " + reserve.getUser().getFirstName()
                    + " " + reserve.getUser().getLastName() + ",\nYou may borrow book",
                    "Book " + book.getTitle() + " is available"));
        }

    }

    private Book getBook(int bookId) {
        //get book which is not deleted
        return bookRepository.findByIdAndIsDeletedFalse(bookId)
                .orElseThrow(() -> new CustomException("Book doesn't exist", HttpStatus.NOT_FOUND));
    }

    private User getUser(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("Invalid Userid", HttpStatus.NOT_FOUND));
    }
}
