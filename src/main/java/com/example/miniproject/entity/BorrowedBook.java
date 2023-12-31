package com.example.miniproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "borrowed_table")
public class BorrowedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer borrowId;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date borrowedDate;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

}
