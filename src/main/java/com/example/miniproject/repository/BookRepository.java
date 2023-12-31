package com.example.miniproject.repository;

import com.example.miniproject.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    public Optional<Book> findByIdAndIsDeletedFalse(int id);

    public List<Book> findAllByIsDeleted(boolean isDeleted);
}
