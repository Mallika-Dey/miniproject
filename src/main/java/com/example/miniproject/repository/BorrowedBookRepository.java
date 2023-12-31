package com.example.miniproject.repository;

import com.example.miniproject.entity.BorrowedBook;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Integer> {
    public boolean existsByBookId(int bookId);

    @Modifying
    @Transactional
    @Query("DELETE FROM BorrowedBook b WHERE b.book.id = :bookId")
    public void deleteByBookId(@Param("bookId") int bookId);
}
