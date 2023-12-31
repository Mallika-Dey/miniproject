package com.example.miniproject.repository;

import com.example.miniproject.entity.ReservedBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservedBookRepository extends JpaRepository<ReservedBook, Integer> {
    public boolean existsByUserIdAndBookId(int userId, int bookId);

    public Optional<ReservedBook> findByUserIdAndBookId(int userId, int bookId);

    public List<ReservedBook> findByBookId(int bookId);
}
