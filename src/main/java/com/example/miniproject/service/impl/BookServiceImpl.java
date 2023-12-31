package com.example.miniproject.service.impl;

import com.example.miniproject.dto.BookDTO;
import com.example.miniproject.dto.BookResponseDTO;
import com.example.miniproject.dto.BookUpdateDto;
import com.example.miniproject.entity.Book;
import com.example.miniproject.exception.CustomException;
import com.example.miniproject.repository.BookRepository;
import com.example.miniproject.repository.ReviewRepository;
import com.example.miniproject.service.BookService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private ReviewRepository reviewRepository;
    private ModelMapper modelMapper;

    @Override
    public void createBook(BookDTO bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);
        bookRepository.save(book);
    }

    @Override
    public void updateBook(int id, BookUpdateDto bookUpdateDto) {
        Book book = getBookById(id);
        book.setTitle(bookUpdateDto.getTitle());
        book.setAuthor(bookUpdateDto.getAuthor());
        book.setDescription(bookUpdateDto.getDescription());
        book.setBookCoverUrl(bookUpdateDto.getBookCoverUrl());
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(int id) {
        Book book = getBookById(id);

        if (!book.isAvailable()) {
            throw new CustomException("You can't delete a borrowed book", HttpStatus.BAD_REQUEST);
        }
        book.setDeleted(true);
        bookRepository.save(book);
    }

    private Book getBookById(int id) {
        return bookRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new CustomException("Book not found with ID: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAllByIsDeleted(false)
                .stream().map(book -> modelMapper.map(book, BookResponseDTO.class)).toList();
    }

	@Override
	public BookResponseDTO getById(int bookId) {
		return modelMapper.map(getBookById(bookId), BookResponseDTO.class);
	}
}
