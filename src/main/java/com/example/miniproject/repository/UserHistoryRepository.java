package com.example.miniproject.repository;

import com.example.miniproject.entity.User;
import com.example.miniproject.entity.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Integer> {
    @Query("SELECT uh FROM UserHistory uh WHERE uh.book.id = :bookId ORDER BY uh.id DESC LIMIT 1")
    public UserHistory findLastByBookId(@Param("bookId") int bookId);

    public List<UserHistory> findAllByUserId(int userId);
}
