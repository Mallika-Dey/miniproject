package com.example.miniproject.service;

import com.example.miniproject.dto.BorrowedBookDTO;
import com.example.miniproject.dto.BorrowedBookResponseDTO;

public interface BorrowedBookService {
    public BorrowedBookResponseDTO borrowBook(int bookId, int userId, BorrowedBookDTO borrowedBookDTO);

    public void returnBook(int bookId, int userId);
}
