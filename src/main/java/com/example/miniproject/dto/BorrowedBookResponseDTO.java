package com.example.miniproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowedBookResponseDTO {
    private String bookTitle;
    private String userName;
    private Date borrowedDate;
    private Date dueDate;
}
