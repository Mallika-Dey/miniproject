package com.example.miniproject.service;

import com.example.miniproject.dto.BookDTO;
import com.example.miniproject.dto.BookResponseDTO;
import com.example.miniproject.dto.BookUpdateDto;
import com.example.miniproject.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    public void createBook(BookDTO book);

    public void updateBook(int id, BookUpdateDto book);

    public void deleteBook(int id);

    public List<BookResponseDTO> getAllBooks();
    
    public BookResponseDTO getById(int bookId);
}
